package exception;

import enums.ResponseCode;

/**
 * @author jzm
 * @date 2022/6/6 : 14:45
 */
public class ApiException extends RuntimeException {

    private Integer code;
    private String errorMsg;
    public ApiException(Integer code, String msg){
        super(msg);
        this.errorMsg = msg;
        this.code = code;
    }

    public ApiException(ResponseCode code){
        super(code.getMsg());
        this.errorMsg = code.getMsg();
        this.code = code.getCode();
    }
}
