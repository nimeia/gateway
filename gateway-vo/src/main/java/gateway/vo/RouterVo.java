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
public class RouterVo implements Serializable {
    private Long id;

    private String code;

    private String name;

    private Long systemId;

    private Integer order;

    private String url;

    private Integer defaultTimeout;

    private Integer defaultConnectionTimeout;

    private String desc;

    private List<FilterVo> filterVos = new ArrayList<>();

    private List<PredicateVo> predicateVos = new ArrayList<>();

}
