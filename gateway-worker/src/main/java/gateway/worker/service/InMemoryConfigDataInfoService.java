package gateway.worker.service;

import gateway.event.ModifyEvent;
import gateway.vo.ClientVo;
import gateway.vo.SystemVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 所有的网关数据会同步到该服务中
 */
@Component
public class InMemoryConfigDataInfoService {

    /**
     * 用于保存所有 client 信息
     */
    Map<String, ClientVo> clientVoMap = new HashMap<>();

    /**
     * 用于保存所有系统信息
     */
    Map<String, SystemVo> stringSystemVoMap = new HashMap<>();;

    /**
     * 删除对应的配置信息
     * @param dataKey
     * @param dataType
     */
    public void delete(String dataKey, ModifyEvent.ModifyDateType dataType) {
        //todo 删除对应的配置信息
    }

    /**
     * 重新初始化所有配置信息
     */
    public void initall() {
        //todo 重新初始化所有配置信息
    }

    /**
     * 更新当前配置信息
     * @param dataKey
     * @param dataType
     */
    public void update(String dataKey, ModifyEvent.ModifyDateType dataType) {
        //todo 更新当前配置信息
    }

    /**
     * 增加对应的配置信息
     * @param dataKey
     * @param dataType
     */
    public void add(String dataKey, ModifyEvent.ModifyDateType dataType) {
        //todo 增加对应的配置信息
    }

    public Map<String, ClientVo> getClientVoMap() {
        return clientVoMap;
    }

    public Map<String, SystemVo> getStringSystemVoMap() {
        return stringSystemVoMap;
    }
}
