package gateway.vo;

import lombok.*;

import java.io.Serializable;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientApiVo implements Serializable {

    private Long id;

    private Long systemId;

    private Long clientId;

    private String code;

    private String url;
}
