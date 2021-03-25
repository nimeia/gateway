package gateway.event;

import com.sun.istack.internal.Nullable;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ModifyEvent implements Serializable {

    private final long timestamp = System.currentTimeMillis();

    private ModifyType type;

    private ModifyAction action;

    private String message;

    @Nullable
    private Object source;

    /**
     * 增加类型
     */
    public enum ModifyAction implements Serializable {
        DELETE,
        UPDATE,
        ADD
    }


    /**
     * 操作的数据类型
     */
    public enum ModifyType implements Serializable {
        /**
         * 接入系统
         */
        TYPE_CLIENT,
        /**
         * 后端系统
         */
        TYPE_SYSTEM,
        /**
         * 路由配置
         */
        TYPE_ROUTE
    }
}
