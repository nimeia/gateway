package gateway.worker.filter.global;

import gateway.vo.ClientApiVo;
import gateway.vo.ClientVo;
import gateway.vo.SystemVo;
import gateway.worker.service.InMemoryConfigDataInfoService;
import gateway.worker.utils.GatewayConstants;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import io.micrometer.core.lang.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class GlobalAuthFilter implements GlobalFilter, Ordered {

    @Autowired
    InMemoryConfigDataInfoService inMemoryConfigDataInfoService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (true) {
            return chain.filter(exchange);
        }

        String token = getHeaderValue(exchange, GatewayConstants.API_TOKEN);
        String apiCode = getHeaderValue(exchange, GatewayConstants.API_CODE);
        String systemCode = getHeaderValue(exchange, GatewayConstants.API_SYSTEM_CODE);
        String appId = getHeaderValue(exchange, GatewayConstants.API_APP_ID);
        String timeout = getHeaderValue(exchange, GatewayConstants.API_TIMEOUT);
        String sign = getHeaderValue(exchange, GatewayConstants.API_REQUEST_SIGN);

        if (token == null || appId == null) {
            return rednerErrorMessage(exchange, "appid or authentication not valid");
        }
        //check if the client exists
        ClientVo clientVo = inMemoryConfigDataInfoService.getClientVoMap().get(appId);
        if (clientVo == null) {
            return rednerErrorMessage(exchange, "appid not valid");
        }
        //check authentication info
        if (!checkAuthInfo(exchange, clientVo, token)) {
            return rednerErrorMessage(exchange, "authentication not valid");
        }
        //check authority info
        if (clientVo != null) {
            Optional<ClientApiVo> first = clientVo.getClientApiVos().stream().filter(x -> apiCode.equals(x.getCode())).findFirst();
            if (!first.isPresent()) {
                return rednerErrorMessage(exchange, "no right !");
            }
        }

        //for read the request boy ,you must add the ReadBodyPredicate
        Object requestBody = exchange.getAttribute("cachedRequestBodyObject");
        if (!checkSignInfo(exchange, clientVo, requestBody)) {
            return rednerErrorMessage(exchange, "no right !");
        }

        return chain.filter(exchange);
    }

    /**
     * check the sign info of the request
     *
     * @param exchange
     * @param clientVo
     * @param requestBody
     * @return
     */
    private boolean checkSignInfo(ServerWebExchange exchange, ClientVo clientVo, @Nullable Object requestBody) {
        //todo 检查报文的验签是否正确
        return false;
    }

    /**
     * check the client have the right to access
     *
     * @param exchange
     * @param clientVo
     * @param token
     * @return
     */
    private boolean checkAuthInfo(ServerWebExchange exchange, ClientVo clientVo, String token) {
        if (clientVo == null || clientVo.getAppSecurity() == null) {
            return false;
        }
        Key key = new SecretKeySpec(Base64Utils.decodeFromString(clientVo.getAppSecurity()), "HmacSHA256");
        Jwt jwtToken;
        try {
            jwtToken = Jwts.parserBuilder().setSigningKey(key).build().parse(token);
        } catch (ExpiredJwtException e) {
            return false;
        } catch (MalformedJwtException e) {
            return false;
        } catch (SignatureException e) {
            return false;
        } catch (IllegalArgumentException e) {
            return false;
        }
        if (jwtToken == null) {
            return false;
        }
        exchange.getAttributes().put(GatewayConstants.JWT, jwtToken);
        return true;
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

    /**
     * render the error message to the client
     *
     * @param exchange
     * @param message
     * @return
     */
    private Mono<Void> rednerErrorMessage(ServerWebExchange exchange, String message) {
        DataBuffer wrap = exchange.getResponse().bufferFactory().wrap(message.getBytes(StandardCharsets.UTF_8));
        return exchange.getResponse().writeWith(Flux.just(wrap));
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
