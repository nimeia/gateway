package gateway.model.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 系统中提供的api
 */
@Entity
@Table(indexes = {
        @Index(name = "idx_api_update_date",columnList = "dateUpdated"),
        @Index(name = "idx_api_system_id",columnList = "systemId"),
})@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class GatewaySystemApi implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private String name;

    private Integer timeout;

    private String url;

    private Long systemId;

    @CreatedDate
    private Date dateCreated;

    @LastModifiedDate
    private Date dateUpdated;

    @Version
    private Integer version;

    @Column(columnDefinition = "text", name = "`desc`")
    private String desc;
}
