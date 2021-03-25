package gateway.api.base.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 基础的page
 *
 * @author huang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString
public class ApiBasePage implements Serializable {

    /**
     * 默认分页大小
     */
    public static Integer DEFAULT_PAGE_SIZE = 20;
    /**
     * 当前页码
     */
    protected Integer page;
    /**
     * 每页大小
     */
    protected Integer pageSize;

}
