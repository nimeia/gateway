package gateway.worker.job;

import gateway.vo.SystemVo;
import gateway.worker.service.InMemoryConfigDataInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * 自动维护各个微信后端接口 auth token
 */
@Component
@Slf4j
public class WechatApiAuthJob {

    /**
     * 微信更新accesstion 使用的锁
     *
     * @param factory
     * @return
     */
    @Bean(name = "wechatLock", destroyMethod = "destroy")
    RedisLockRegistry redisLockRegistry(RedisConnectionFactory factory) {
        return new RedisLockRegistry(factory, "lock:wechat:", 5 * 60 * 1000L);
    }

    @Autowired
    @Qualifier("wechatLock")
    RedisLockRegistry redisLock;

    @Autowired
    InMemoryConfigDataInfoService inMemoryConfigDataInfoService;

    @Scheduled(initialDelay = 2 * 60 * 1000, fixedRate = 2 * 60 * 1000)
    void updateAccessToken() {
        log.info("===========update the access token start===================");
        Map<String, SystemVo> systemsMap = inMemoryConfigDataInfoService.getStringSystemVoMap();
        for (SystemVo systemvo : systemsMap.values()) {
            if (!(SystemVo.SystemType.wechat.toString().equals(systemvo.getSystemType())
                    || SystemVo.SystemType.wechatEnterprise.toString().equals(systemvo.getSystemType())
                    || SystemVo.SystemType.dingding.toString().equals(systemvo.getSystemType()))
            ) {
                continue;
            }
            try {
                //先检查当前是否要取access token ,再去取redislock
                Lock lock = redisLock.obtain("system:" + systemvo.getCode());
                if (lock.tryLock(60, TimeUnit.SECONDS)) {
                    try {
                        lock.lock();
                        //todo  更新access token 并推送到各个服务实例中 ，是否需要更新，按实际情况决定
                    } finally {
                        lock.unlock();
                    }
                }
            } catch (Exception e) {
                log.error(e.getLocalizedMessage(), e);
            }
        }
        log.info("===========update the access token end===================");
    }

}
