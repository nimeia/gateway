package gateway.vo;

import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientVo implements Serializable {

    private Long id;

    private String name;

    private String appId;

    private String password;

    private String appSecurity;

    /**
     * oauth 2 认证时的临时解密key
     */
    private String oauth2SignKey;

    private Date vaildDate;

    private Boolean tokenCanCreate;

    private Boolean vaildFlag;

    private String desc;

    private List<ClientApiVo> clientApiVos = new ArrayList<>();
}
