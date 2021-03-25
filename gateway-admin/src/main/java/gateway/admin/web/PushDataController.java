package gateway.admin.web;

import gateway.admin.service.InRedisConfigDataService;
import gateway.api.base.response.ApiSimpleResponse;
import gateway.event.InitAllEvent;
import gateway.utils.GatewayVoConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 负责把配置的数据一次加载到 redis 中，
 * 用于系统初始化加载数据
 */
@RequestMapping("push")
public class PushDataController {

    @Autowired
    InRedisConfigDataService inRedisConfigDataService;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    /**
     * 初始化所有数据到 redis 并发送更新通知
     *
     * @return
     */
    public ApiSimpleResponse<Boolean> initAllData() {
        // todo 初始化所有数据到 redis 并发送更新通知
        inRedisConfigDataService.initAllDataToRedis();
        redisTemplate.convertAndSend(GatewayVoConstants.REDIS_UPDATE_EVENT_CHANNEL,
                InitAllEvent.builder().message("update all").source("system admin").build());
        return ApiSimpleResponse.<Boolean>builder().message("success").data(true).build();
    }
}
