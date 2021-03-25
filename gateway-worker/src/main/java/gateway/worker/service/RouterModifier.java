package gateway.worker.service;

import gateway.vo.FilterVo;
import gateway.vo.PredicateVo;
import gateway.vo.RouterVo;
import io.jsonwebtoken.lang.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.InMemoryRouteDefinitionRepository;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.*;

@Component
@Slf4j
public class RouterModifier {

    @Autowired
    InMemoryRouteDefinitionRepository inMemoryRouteDefinitionRepository;

    @Autowired
    ApplicationEventPublisher publisher;


    public void saveRouter(List<RouterVo> routerVos) {
        if(routerVos!=null){
            for (RouterVo routerVo : routerVos) {
                saveRouter(routerVo);
            }
        }
    }

    public void  saveRouter(RouterVo routerVo){
        log.info("add router to system: {}",routerVo);
        RouteDefinition routeDefinition = new RouteDefinition();
        routeDefinition.setId(routerVo.getId().toString());
        routeDefinition.setOrder(routerVo.getOrder());
        routeDefinition.setUri(URI.create(routerVo.getUrl()));

        List<PredicateDefinition> predicateDefinitions = new ArrayList<>();

        PredicateDefinition requestBodyPredicateDefinition = new PredicateDefinition();
        requestBodyPredicateDefinition.setName("ReadBody");
        requestBodyPredicateDefinition.setArgs(Maps.of("inClass","java.lang.String")
                .and("predicate","gateway.worker.predicate.TestPredicate").build());
        predicateDefinitions.add(requestBodyPredicateDefinition);

        if(routerVo.getPredicateVos()!=null){
            for (PredicateVo predicateVo : routerVo.getPredicateVos()) {
                PredicateDefinition predicateDefinition = new PredicateDefinition();
                predicateDefinition.setArgs(predicateVo.getArgs());
                predicateDefinition.setName(predicateVo.getName());
                predicateDefinitions.add(predicateDefinition);
            }
        }
        routeDefinition.setPredicates(predicateDefinitions);

        List<FilterDefinition> filterDefinitions = new ArrayList<>();
        if(routerVo.getFilterVos()!=null){
            for (FilterVo filterVo : routerVo.getFilterVos()) {
                FilterDefinition filterDefinition = new FilterDefinition();
                filterDefinition.setName(filterVo.getName());
                filterDefinition.setArgs(filterVo.getArgs());
                filterDefinitions.add(filterDefinition);
            }
        }
        if(filterDefinitions.size()>0){
            routeDefinition.setFilters(filterDefinitions);
        }

        inMemoryRouteDefinitionRepository.save(Mono.just(routeDefinition)).subscribe();
        publisher.publishEvent(new RefreshRoutesEvent(this));
    }
}
