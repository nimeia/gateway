package gateway.worker.config;

import gateway.worker.init.SystemStartUpConfigDataInitialize;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.util.function.Predicate;

@Configuration
@Slf4j
@EnableScheduling
@EnableAsync
public class GlobalConfig implements ApplicationContextAware {

    ApplicationContext applicationContext;

    @Autowired
    SystemStartUpConfigDataInitialize systemStartUpConfigDataInitialize;



    @Autowired
    @Qualifier("webFluxConversionService")
    void setGenericConversionService(GenericConversionService genericConversionService) {
        // 用于在配置路由时自动把字符串转成 bean
        genericConversionService.addConverter(new Converter<String, Predicate>() {
            @Override
            public Predicate convert(String source) {
                try {
                    return applicationContext.getBean(source, Predicate.class);
                } catch (BeansException e) {
                    log.error(e.getLocalizedMessage(), e);
                }
                try {
                    boolean assignableFrom = Predicate.class.isAssignableFrom(Class.forName(source));
                    if (assignableFrom) {
                        return (Predicate) Class.forName(source).newInstance();
                    }
                } catch (ClassNotFoundException e) {
                    log.error(e.getLocalizedMessage(), e);
                } catch (IllegalAccessException e) {
                    log.error(e.getLocalizedMessage(), e);
                } catch (InstantiationException e) {
                    log.error(e.getLocalizedMessage(), e);
                }
                return null;
            }
        });
    }

    @Bean("reactiveRedisTemplate")
    ReactiveRedisTemplate<String, Object> reactiveRedisTemplate(@Autowired ReactiveRedisConnectionFactory reactiveRedisConnectionFactory) {

        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();

        RedisSerializationContext serializationContext = RedisSerializationContext
                .<String, Object>newSerializationContext()
                .key(RedisSerializer.string())
                .value(genericJackson2JsonRedisSerializer)
                .hashKey(stringRedisSerializer)
                .hashValue(genericJackson2JsonRedisSerializer)
                .build();

        return new ReactiveRedisTemplate(reactiveRedisConnectionFactory, serializationContext);
    }

    @PostConstruct
    void init() {
        log.info("==========================");
        log.info("start to load system config data ");
        systemStartUpConfigDataInitialize.doInitJob();
        log.info("end from load system config data ");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
