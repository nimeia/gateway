package gateway.api.base.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * api 基础查询对象
 *
 * @author huang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString
public class ApiSimpleRequest<T extends Serializable> implements Serializable {

    /**
     * 标志某一个请求,可以按其跟踪一个接口的请求
     *
     */
    protected String requestId;

    /**
     * 请求时间
     */
    protected Date requestDate;

    /**
     * 一些接口可能需要定义较长的超时时间,通过该属性修改处理
     *
     * @ignore
     */
    protected Long timeOut;

    /**
     * 标记一个用户的信息,在从报文体中分开
     * @ignore
     */
    protected String token;

    /**
     * the business data
     *
     * @see T
     */
    protected T data;

}
