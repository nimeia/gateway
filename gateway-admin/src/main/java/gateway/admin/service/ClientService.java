package gateway.admin.service;

import gateway.admin.utils.mapper.ClientMapper;
import gateway.model.entity.GatewayClient;
import gateway.model.repos.GatewayClientRepos;
import gateway.vo.ClientVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@Transactional(readOnly = true)
public class ClientService {

    @Autowired
    GatewayClientRepos gatewayClientRepos;

    @Transactional(readOnly = false)
    public ClientVo save(ClientVo data) {
        GatewayClient gatewayClient = ClientMapper.mapper.toGatewayClient(data);
        gatewayClientRepos.save(gatewayClient);
        return ClientMapper.mapper.toClientVo(gatewayClient);
    }
}
