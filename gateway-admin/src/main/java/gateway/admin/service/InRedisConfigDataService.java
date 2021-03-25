package gateway.admin.service;

import gateway.model.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class InRedisConfigDataService {

    @Autowired
    GatewayClientApiRepos gatewayClientApiRepos;

    @Autowired
    GatewayClientRepos gatewayClientRepos;

    @Autowired
    GatewaySystemApiRepos gatewaySystemApiRepos;

    @Autowired
    GatewaySystemRepos gatewaySystemRepos;

    @Autowired
    GatewaySystemRouterFilterRepos gatewaySystemRouterFilterRepos;

    @Autowired
    GatewaySystemRouterPredicateRepos gatewaySystemRouterPredicateRepos;

    @Autowired
    GatewaySystemRouterRepos gatewaySystemRouterRepos;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    /**
     * 初始化所有数据到 redis ,需要手动发送更新消息到各个worker
     */
    public void initAllDataToRedis() {
        //todo send all to data to redis ,you can`t send the model ，you need change the model to vo and then send it

    }
}
