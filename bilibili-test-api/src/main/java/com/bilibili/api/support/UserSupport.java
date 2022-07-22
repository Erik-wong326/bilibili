package com.bilibili.api.support;

import com.bilibili.exception.ConditionException;
import com.bilibili.service.util.TokenUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 用户相关的支撑功能
 * 通用方法
 * @author Erik_Wong
 * @version 1.0
 * @date 2022/7/22 17:16
 */
@Component
public class UserSupport {

    /**
     * 1.获取当前用户Id
     * @return 用户id
     */
    public Long getCurrentUserId(){
        //1.SpringBoot 提供的抓取请求上下文的方法
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //从header中获取token
        String token = requestAttributes.getRequest().getHeader("token");
        Long userId = TokenUtil.verifyToken(token);
        if (userId < 0){
            throw new ConditionException("非法用户!");
        }
        return userId;
    }
}
