package gateway.vo;

import lombok.*;

import java.io.Serializable;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiVo implements Serializable {

    private Long id;

    private String code;

    private String name;

    private Integer timeout;

    private String url;

    private Long systemId;

    private String desc;
}
