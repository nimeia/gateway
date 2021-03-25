package gateway.model.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 客户端，指代服务的调用方
 *
 * @author huang
 * @version 1.0.0
 */
@Entity
@Table(indexes = {
        @Index(name = "idx_client_update_date",columnList = "dateUpdated"),
        @Index(name = "idx_client_app_id",columnList = "appId",unique = true),
})
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class GatewayClient implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20)
    private String name;

    @Column(length = 20)
    private String appId;

    @Column(length = 100)
    private String password;

    @Column(length = 200)
    private String appSecurity;

    private Date vaildDate;

    /**
     * 是否可以自行生成token
     */
    private Boolean tokenCanCreate;

    private Boolean vaildFlag;

    @CreatedDate
    private Date dateCreated;

    @LastModifiedDate
    private Date dateUpdated;

    @Version
    private Integer version;

    @Column(columnDefinition = "text", name = "`desc`")
    private String desc;

}
