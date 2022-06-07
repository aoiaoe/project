package api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiRequest {
    /**
     * 客户端唯一编号
     */
    private String appId;

    /**
     * 加密后业务相关的入参
     */
    private String data;
}
