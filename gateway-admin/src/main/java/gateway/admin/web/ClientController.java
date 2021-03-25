package gateway.admin.web;

import gateway.admin.service.ClientService;
import gateway.api.base.request.ApiSimpleRequest;
import gateway.api.base.response.ApiSimpleResponse;
import gateway.vo.ClientVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */

@RequestMapping("client")
@RestController
public class ClientController {

    @Autowired
    ClientService clientService;

    @PostMapping("/add")
    public ApiSimpleResponse<ClientVo> addClient(@RequestBody ApiSimpleRequest<ClientVo> request) {
        ClientVo clientVo = clientService.save(request.getData());
        return ApiSimpleResponse.<ClientVo>builder()
                .data(clientVo)
                .success(true)
                .requestId(request.getRequestId())
                .message("success")
                .build();
    }

}
