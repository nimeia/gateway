package gateway.admin.utils.mapper;

import gateway.model.entity.GatewayClient;
import gateway.vo.ClientVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClientMapper {

    ClientMapper mapper = Mappers.getMapper(ClientMapper.class);

    @Mapping(target = "id", ignore = true)
    GatewayClient toGatewayClient(ClientVo clientVo);

    void mergeGatewayClient(ClientVo clientVo, @MappingTarget GatewayClient client);

    ClientVo toClientVo(GatewayClient gatewayClient);
}
