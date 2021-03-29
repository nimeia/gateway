package gateway.worker.filter;

import gateway.vo.SystemVo;
import gateway.worker.service.InMemoryConfigDataInfoService;
import gateway.worker.utils.GatewayConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractNameValueGatewayFilterFactory;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;

/**
 * 用于调用微信服务器自动增加token
 */
@Component
public class WechatAuthFilter extends AbstractNameValueGatewayFilterFactory {

    @Autowired
    InMemoryConfigDataInfoService inMemoryConfigDataInfoService;

    @Override
    public GatewayFilter apply(NameValueConfig config) {
        return new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

                //todo 微信公众号接口调用自动增加 access_token 参数
                String systemCode = getHeaderValue(exchange, GatewayConstants.API_SYSTEM_CODE);

                SystemVo systemVo = inMemoryConfigDataInfoService.getSystemVoMap().get(systemCode);
                if(systemVo!=null){
                    URI uri = exchange.getRequest().getURI();
                    StringBuilder query = new StringBuilder();
                    String originalQuery = uri.getRawQuery();
                    if (StringUtils.hasText(originalQuery)) {
                        query.append(originalQuery);
                        if (originalQuery.charAt(originalQuery.length() - 1) != '&') {
                            query.append('&');
                        }
                    }

                    String value = systemVo.getCurrentAccessToken();
                    // TODO urlencode?
                    query.append("access_token");
                    query.append('=');
                    query.append(value);

                    try {
                        URI newUri = UriComponentsBuilder.fromUri(uri).replaceQuery(query.toString()).build(true).toUri();

                        ServerHttpRequest request = exchange.getRequest().mutate().uri(newUri).build();

                        return chain.filter(exchange.mutate().request(request).build());
                    }
                    catch (RuntimeException ex) {
                        throw new IllegalStateException("Invalid URI query: \"" + query.toString() + "\"");
                    }
                }
                return chain.filter(exchange);
            }
        };
    }

    /**
     * get a single value from the header
     *
     * @param exchange
     * @param key
     * @return
     */
    private String getHeaderValue(ServerWebExchange exchange, String key) {
        List<String> value = exchange.getRequest().getHeaders().get(key);
        if (value != null && value.size() == 1) {
            return value.get(0);
        }
        return null;
    }
}
