package com.bilibili.service.handler;

import com.bilibili.domain.JsonResponse;
import com.bilibili.exception.ConditionException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Erik_Wong
 * @version 1.0
 * @date 2022/7/16 17:01
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CommonGlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public JsonResponse<String> commonExceptionHandler(HttpServletRequest request,Exception e){
        String errorMsg = e.getMessage();
        if (e instanceof ConditionException){
            //定制化异常ConditionException的情况
            String errorCode = ((ConditionException)e).getCode();
            return new JsonResponse<>(errorCode,errorMsg);
        }else {
            //通用异常RuntimeException的情况
            return new JsonResponse<>("500",errorMsg);
        }
    }
}
