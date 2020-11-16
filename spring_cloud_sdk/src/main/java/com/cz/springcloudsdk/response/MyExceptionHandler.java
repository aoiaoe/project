package com.cz.springcloudsdk.response;

import com.cz.springcloudsdk.enums.ErrorCodeEnums;
import com.cz.springcloudsdk.error.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author alian
 * @date 2020/11/16 上午 11:56
 * @since JDK8
 */
@Slf4j
@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultResponse handle(Exception e) {
        log.error("error:{}", e);
        if (e instanceof ServiceException) {   //判断异常是否是我们定义的异常
            ServiceException serviceException = (ServiceException) e;
            return new ResultResponse(serviceException.getCode(), serviceException.getMessage());
        }
        return new ResultResponse(ErrorCodeEnums.FAIL.getCode(), e.getMessage());
    }
}
