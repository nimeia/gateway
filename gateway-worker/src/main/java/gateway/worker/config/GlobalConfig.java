package gateway.worker.config;

import gateway.worker.config.init.DevInitData;
import gateway.worker.init.SystemStartUpConfigDataInitialize;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.*;

import javax.annotation.PostConstruct;

@Configuration
@Slf4j
public class GlobalConfig {

    @Autowired
    SystemStartUpConfigDataInitialize systemStartUpConfigDataInitialize;

    @Bean("reactiveRedisTemplate")
    ReactiveRedisTemplate<String,Object>  reactiveRedisTemplate(@Autowired ReactiveRedisConnectionFactory reactiveRedisConnectionFactory){

        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();

        RedisSerializationContext serializationContext = RedisSerializationContext
                .<String,Object>newSerializationContext()
                .key(RedisSerializer.string())
                .value(genericJackson2JsonRedisSerializer)
                .hashKey(stringRedisSerializer)
                .hashValue(genericJackson2JsonRedisSerializer)
                .build();

        return new ReactiveRedisTemplate(reactiveRedisConnectionFactory, serializationContext);
    }

    @PostConstruct
    void init(){
        log.info("==========================");
        log.info("start to load system config data ");
        systemStartUpConfigDataInitialize.doInitJob();
        log.info("end from load system config data ");
    }

}
