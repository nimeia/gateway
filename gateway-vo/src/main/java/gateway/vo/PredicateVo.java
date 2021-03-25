package gateway.vo;

import lombok.*;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PredicateVo implements Serializable {

    private Long id;

    private Long routerId;

    private Integer order;

    private String name;

    private Map<String, String> args = new LinkedHashMap<>();

    private String desc;
}
