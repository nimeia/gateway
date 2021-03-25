package gateway.admin.web;

import gateway.admin.web.vo.SystemVo;
import gateway.api.base.request.ApiSimpleRequest;
import gateway.api.base.response.ApiSimpleResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("system")
public class SystemController {


    @PostMapping("add")
    public ApiSimpleResponse<SystemVo> addSystem(@RequestBody ApiSimpleRequest<SystemVo> request) {
        return null;
    }
}
