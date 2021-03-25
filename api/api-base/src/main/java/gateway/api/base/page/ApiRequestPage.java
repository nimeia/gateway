package gateway.api.base.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 带排序的分页
 *
 * @author huang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class ApiRequestPage extends ApiBasePage implements Serializable {

    /**
     * 排序参数，orderBy=name desc,hireDate
     *
     * @mock orderBy=name desc,hireDate
     */
    private String orderBy;

    /**
     * 过滤条件，filter=(name eq 'Milk' or name eq 'Eggs') and price lt 2.55
     *
     * @mock filter=(name eq 'Milk' or name eq 'Eggs') and price lt 2.55
     */
    private String filter;
}
