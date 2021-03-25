package gateway.model.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 系统api的路由信息
 */
@Entity
@Table(indexes = {
        @Index(name = "idx_router_update_date",columnList = "dateUpdated"),
        @Index(name = "idx_router_system_id",columnList = "systemId"),
})@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class GatewaySystemRouter implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private String name;

    private String url;

    private Long systemId;

    @Column(name = "`order`")
    private Integer order;

    private Integer defaultTimeout;

    private Integer defaultConnectionTimeout;

    @CreatedDate
    private Date dateCreated;

    @LastModifiedDate
    private Date dateUpdated;

    @Version
    private Integer version;

    @Column(columnDefinition = "text", name = "`desc`")
    private String desc;
}
