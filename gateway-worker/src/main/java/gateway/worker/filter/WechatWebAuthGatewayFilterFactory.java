package gateway.worker.filter;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static gateway.worker.filter.WechatWebAuthGatewayFilterFactory.*;

/**
 * 处理需要通过 oauth2 网页授权的流程
 */
@Component
@Slf4j
public class WechatWebAuthGatewayFilterFactory extends AbstractGatewayFilterFactory<Config> {

    public WechatWebAuthGatewayFilterFactory(){
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                log.info("config:{}",config);
                //todo 根据具体的url规范判断是否获取用户 code ,回调后自动获取 access_token
                return chain.filter(exchange);
            }
        };
    }

    @Data
    @ToString
    public static class Config{

        private String codeUrl;

        private String tokenUrl;

    }
}
