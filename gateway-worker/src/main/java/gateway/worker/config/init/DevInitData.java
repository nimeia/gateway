package gateway.worker.config.init;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.map.MapUtil;
import gateway.vo.*;
import gateway.worker.service.InMemoryConfigDataInfoService;
import gateway.worker.service.RouterModifier;
import io.jsonwebtoken.lang.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.GenericConversionService;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

/**
 * 开发测试用
 */
@Configuration
@Profile("dev")
@Slf4j
public class DevInitData {
    @Autowired
    InMemoryConfigDataInfoService inMemoryConfigDataInfoService;

    @Autowired
    RouterModifier routerModifier;

    @Autowired
    GenericConversionService genericConversionService;

    @PostConstruct
    void initDevData() {

        genericConversionService.addConverter(new Converter<String, java.util.function.Predicate>() {
            @Override
            public Predicate convert(String source) {
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

        Map<String, ClientVo> clientVoMap = inMemoryConfigDataInfoService.getClientVoMap();
        Map<String, SystemVo> stringSystemVoMap = inMemoryConfigDataInfoService.getStringSystemVoMap();

        ClientVo clientVo = ClientVo.builder().id(1L).appId("cliam-sys").appSecurity("=====").password("pssword")
                .name("cliam-system").vaildDate(DateTime.now().offset(DateField.YEAR, 1)).vaildFlag(true)
                .tokenCanCreate(true)
                .build();

        ApiVo apiVo = ApiVo.builder().code("policy/query").id(1L).name("policy-query-api").url("/policy/query").build();

        SystemVo systemVo = SystemVo.builder()
                .code("policy")
                .id(1000L)
                .desc("承保")
                .build();

        //init authority
        ClientApiVo clientApiVo = ClientApiVo.builder().id(1L).code(apiVo.getCode()).clientId(clientVo.getId())
                .systemId(systemVo.getId()).url(apiVo.getUrl()).build();
        clientVo.setClientApiVos(Arrays.asList(clientApiVo));

        clientVoMap.put(clientVo.getAppId(), clientVo);

        //init routes
        RouterVo routerVo = RouterVo.builder().id(1L).name("test router").code("test-router").defaultTimeout(1000).order(1)
                .url("https://httpbin.org/")
                .systemId(systemVo.getId()).defaultConnectionTimeout(100).build();

//        FilterVo filterVo = FilterVo.builder().id(1L).name("").build();
//        routerVo.setFilterVos(Arrays.asList(filterVo));
        FilterVo filterVo = FilterVo.builder().id(12L).name("EdiRequestBody")
                .args(MapUtil.<String,String>builder().put("key","hello").build()).build();
        routerVo.setFilterVos(Arrays.asList(filterVo));

        PredicateVo predicateVo = PredicateVo.builder().id(1L).name("Path").order(1).routerId(routerVo.getId()).args(
                Maps.of("patterns", "/anything").build()
        ).build();
        routerVo.setPredicateVos(Arrays.asList(predicateVo));
        systemVo.setRouterVos(Arrays.asList(routerVo));

        stringSystemVoMap.put(systemVo.getCode(), systemVo);

        routerModifier.saveRouter(systemVo.getRouterVos());
    }
}
