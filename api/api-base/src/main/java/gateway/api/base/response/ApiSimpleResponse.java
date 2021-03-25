package gateway.api.base.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 基础返回对象
 *
 * @param <T>
 * @author huang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString
public class ApiSimpleResponse<T> implements Serializable {

    public static final String RESPONSE_CODE_SUCCESS = "200";
    public static final String RESPONSE_CODE_ERROR = "500";
    public static final String RESPONSE_LOGIN_NEED = "401";
    public static final String RESPONSE_FORBIDDEN = "403";

    /**
     * 标志某一个请求,可以按其跟踪一个接口的请求
     */
    protected String requestId;

    /**
     * 系统识别号
     */
    protected String system;

    /**
     * 返回结果码
     */
    protected String code;

    /**
     * 业务上是否成功
     */
    protected Boolean success;

    /**
     * 调用结果信息
     */
    protected String message;

    /**
     * 业务返回信息描述
     */
    protected String businessMessage;

    /**
     * the business data
     *
     * @see T
     */
    protected T data;

}
