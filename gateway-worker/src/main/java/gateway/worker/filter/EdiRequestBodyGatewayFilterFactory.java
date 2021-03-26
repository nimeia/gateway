package gateway.worker.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.rewrite.CachedBodyOutputMessage;
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyRequestBodyGatewayFilterFactory;
import org.springframework.cloud.gateway.support.BodyInserterContext;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * only a demo for rewrite request body
 */
//@Component
@Slf4j
public class EdiRequestBodyGatewayFilterFactory extends AbstractGatewayFilterFactory<EdiRequestBodyGatewayFilterFactory.Config> {

    public EdiRequestBodyGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                System.out.println("-----------------");
                System.out.println(config.getKey());
                exchange.getRequest().getBody().subscribe(System.out::println);
                exchange.getRequest().getBody().subscribe(System.out::println);
                HttpHeaders headers = new HttpHeaders();
                headers.putAll(exchange.getRequest().getHeaders());
                headers.remove("Content-Length");
//                if (config.getContentType() != null) {
//                    headers.set("Content-Type", config.getContentType());
//                }

                BodyInserter<String, ReactiveHttpOutputMessage> bodyInserter = BodyInserters.fromValue("{\"google\":1234}");
                CachedBodyOutputMessage outputMessage = new CachedBodyOutputMessage(exchange,headers);
                System.out.println("-----------------");
                return bodyInserter.insert(outputMessage, new BodyInserterContext()).then(Mono.defer(() -> {
                    ServerHttpRequestDecorator decorator = new ServerHttpRequestDecorator(exchange.getRequest()){

                        @Override
                        public HttpHeaders getHeaders() {
                            return headers;
                        }

                        @Override
                        public Flux<DataBuffer> getBody() {
                            return outputMessage.getBody();
                        }
                    };
                    return chain.filter(exchange.mutate().request(decorator).build());
//                    return chain.filter(exchange);
                })).onErrorResume((throwable) -> {
                    log.error(throwable.getLocalizedMessage(),throwable);
                    return Mono.empty();
                });
//                return chain.filter(exchange);
            }
        };
    }

    @Data
    public static class Config{
        private String key;
    }
}
