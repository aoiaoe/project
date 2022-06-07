package response;

import enums.ResponseCode;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jzm
 * @date 2022/6/6 : 14:07
 */
@NoArgsConstructor
@Data
public class CommonResponse<T> {

    /**
     * 是否成功
     */
    private boolean success = true;

    private Integer code;

    private String msg;

    private T data;

    public static <T> CommonResponse suc(T data){
        return new CommonResponse(ResponseCode.REQUEST_SUC, data);
    }

    public CommonResponse(Integer code, String msg, T data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public CommonResponse(ResponseCode code, T data){
        this(code.getCode(), code.getMsg(), data);
    }

    public static CommonResponse error(ResponseCode code) {
        return new CommonResponse(code.getCode(), code.getMsg(), null);
    }
}
