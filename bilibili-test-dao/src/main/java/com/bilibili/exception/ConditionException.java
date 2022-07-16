package com.bilibili.exception;

/**
 * @author Erik_Wong
 * @version 1.0
 * @date 2022/7/16 17:03
 */
public class ConditionException extends RuntimeException{
    //序列化版本id
    private static final long serialVersionUID = 1L;

    //响应状态码
    private String code;

    public ConditionException(String code,String name){
        super(name);
        this.code = code;
    }

    public ConditionException(String name){
        super(name);
        code = "500";
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
