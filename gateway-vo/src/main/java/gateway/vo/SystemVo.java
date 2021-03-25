package gateway.vo;

import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
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

    private List<RouterVo> routerVos = new ArrayList<>();

    private List<ApiVo> apiVos = new ArrayList<>();

}
