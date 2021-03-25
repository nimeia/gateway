package gateway.model.entity;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Entity
@Table(indexes = {
        @Index(name = "idx_predicate_update_date",columnList = "dateUpdated"),
        @Index(name = "idx_predicate_router_id",columnList = "routerId"),
})
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class GatewaySystemRouterPredicate implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long routerId;

    @Column(name = "`order`")
    private Integer order;

    private String name;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "gateway_predicate_values",
            joinColumns = {@JoinColumn(name = "filter_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "arg_name")
    @Column(name = "arg_value")
    private Map<String, String> args = new LinkedHashMap<>();

    @CreatedDate
    private Date dateCreated;

    @LastModifiedDate
    private Date dateUpdated;

    @Version
    private Integer version;

    @Column(columnDefinition = "text", name = "`desc`")
    private String desc;
}
