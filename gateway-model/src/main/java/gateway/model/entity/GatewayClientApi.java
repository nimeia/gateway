package gateway.model.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.repository.Modifying;

import javax.persistence.*;
import java.io.Serializable;


/**
 * 接入系统权限配置
 */
@Entity
@Table(indexes = {
        @Index(name = "idx_client_api_system_id", columnList = "systemId"),
        @Index(name = "idx_client_api_app_id", columnList = "appId"),
})
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class GatewayClientApi implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false, nullable = false)
    private Long systemId;

    @Column(updatable = false, nullable = false)
    private Long clientId;

    @Column(updatable = false, nullable = false)
    private String code;

    @Column(updatable = false, nullable = false)
    private String url;

}
