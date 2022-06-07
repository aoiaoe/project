package api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 返回信息
     */
    private String msg;

    /**
     * 响应码
     */
    private Integer code;

    /**
     * 业务相关的返回信息，私钥加密之后的
     */
    private String data;

}
