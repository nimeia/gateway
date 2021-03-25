package gateway.worker.init;

import gateway.worker.service.RouterModifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * 从数据库中初始化
 */
@Component
@ConditionalOnProperty(prefix = "gateway", name = "system-init-load-type", havingValue = "redis", matchIfMissing = false)
public class InRedisSystemStartUpConfigDataInitialize implements SystemStartUpConfigDataInitialize {

    @Autowired
    RouterModifier routerModifier;

    @Autowired
    ReactiveRedisTemplate<String,Object> reactiveRedisTemplate;

    @Override
    public void doInitJob() {
        //todo 加载所有数据到本机的jvm中，方便后续处理

        //todo 同时需要把路由数据刷新的spring cloud gate 的 InMemoryRouteDefinitionRepository 中
        //routerModifier.saveRouter();
    }
}
