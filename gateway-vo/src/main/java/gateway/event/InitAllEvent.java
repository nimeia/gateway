package gateway.event;

import com.sun.istack.internal.Nullable;
import lombok.*;

import java.io.Serializable;

/**
 * 更新所有配置信息事件
 */
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class InitAllEvent implements Serializable {

    private final long timestamp = System.currentTimeMillis();

    private String message;

    @Nullable
    private Object source;
}
