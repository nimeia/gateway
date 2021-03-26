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
public class SystemVo implements Serializable {
    private Long id;

    private String code;

    private String name;

    private String desc;

    /**
     * 微信 企业微信 钉钉 等
     */
    private String systemType;

    private String appId;

    private String appSecurity;

    private String currentAccessToken;

    private Date accessTokenExpiredTime;

    private List<RouterVo> routerVos = new ArrayList<>();

    private List<ApiVo> apiVos = new ArrayList<>();

    public enum SystemType{
        wechat,wechatEnterprise,dingding
    }
}
