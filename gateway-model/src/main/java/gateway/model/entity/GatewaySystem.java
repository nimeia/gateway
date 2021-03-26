package gateway.model.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 后端服务系统
 */
@Entity
@Table(indexes = {
        @Index(name = "idx_system_update_date",columnList = "dateUpdated"),
})
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class GatewaySystem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private String name;

    /**
     * 微信 企业微信 钉钉 等
     */
    private String systemType;

    private String appId;

    private String appSecurity;

    @CreatedDate
    private Date dateCreated;

    @LastModifiedDate
    private Date dateUpdated;

    @Version
    private Integer version;

    @Column(columnDefinition = "text", name = "`desc`")
    private String desc;
}
