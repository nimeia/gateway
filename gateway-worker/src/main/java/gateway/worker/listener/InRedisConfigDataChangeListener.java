package gateway.worker.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import gateway.event.ModifyEvent;
import gateway.utils.GatewayVoConstants;
import gateway.worker.service.InMemoryConfigDataInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.ReactiveSubscription;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.ReactiveRedisMessageListenerContainer;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class InRedisConfigDataChangeListener {

    @Autowired
    ReactiveRedisConnectionFactory factory;

    @Autowired
    ReactiveRedisTemplate<String, Object> reactiveRedisTemplate;

    @Autowired
    InMemoryConfigDataInfoService inMemoryConfigDataInfoService;

    private final ObjectMapper mapper = new ObjectMapper();
    {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
    }

    @PostConstruct
    void init() {
        log.info("start the listener for update data ");
        ReactiveRedisMessageListenerContainer container = new ReactiveRedisMessageListenerContainer(factory);

        Flux<ReactiveSubscription.Message<String, String>> receive = container.receive(
                ChannelTopic.of(GatewayVoConstants.REDIS_UPDATE_EVENT_CHANNEL));

        receive.publishOn(Schedulers.newSingle("redis-update-data-job")).subscribe(s -> {
            log.info(s.toString());
            //todo 处理通知信息，
            try {
                ModifyEvent modifyEvent = mapper.readValue(s.getMessage(), ModifyEvent.class);
                if(modifyEvent.getAction().equals(ModifyEvent.ModifyAction.DELETE)) {
                    inMemoryConfigDataInfoService.delete(modifyEvent.getDataKey(),modifyEvent.getDataType());
                }else if(modifyEvent.getAction().equals(ModifyEvent.ModifyAction.INIT_ALL)){
                    inMemoryConfigDataInfoService.initall();
                }else if(modifyEvent.getAction().equals(ModifyEvent.ModifyAction.UPDATE)){
                    inMemoryConfigDataInfoService.update(modifyEvent.getDataKey(),modifyEvent.getDataType());
                }else if(modifyEvent.getAction().equals(ModifyEvent.ModifyAction.ADD)){
                    inMemoryConfigDataInfoService.add(modifyEvent.getDataKey(),modifyEvent.getDataType());
                }else if(modifyEvent.getAction().equals(ModifyEvent.ModifyAction.ACCESS_TOKEN_UPDATE)){
                    inMemoryConfigDataInfoService.updateAccessTokenOnly(modifyEvent.getDataKey(),modifyEvent.getDataType());
                }
                System.out.println(modifyEvent);
            } catch (JsonProcessingException e) {
                log.error(e.getMessage(), e);
            }
        });

    }

}
