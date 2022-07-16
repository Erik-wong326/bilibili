package com.bilibili.domain;

/**
 * @author Erik_Wong
 * @version 1.0
 * @date 2022/7/16 16:26
 */
public class JsonResponse<T> {
    private String code;
    private String msg;
    private T data;

    public JsonResponse(String code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public JsonResponse(T data){
        this.data = data;
        msg = "success";
        code = "200";
    }

    /**
     * 用于不需要返回给前端,但是请求成功的场景
     * 如 Get
     * @return null
     */
    public static JsonResponse<String> success(){
        return new JsonResponse<String>(null);
    }

    /**
     * 用于请求成功且需要返回给前端String的场景
     * 如:令牌
     * @param data
     * @return data
     */
    public static JsonResponse<String> success(String data){
        return new JsonResponse<>(data);
    }


    /**
     * 请求失败且不需要参数的场景
     * @return data
     */
    public static JsonResponse<String> fail(){
        return new JsonResponse<>("500","数据请求失败,请检查!");
    }

    /**
     * 请求失败,且需要返回参数
     * @param code 失败码
     * @param msg 失败信息
     * @return data
     */
    public static JsonResponse<String> fail(String code,String msg){
        return new JsonResponse<>(code,msg);
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
