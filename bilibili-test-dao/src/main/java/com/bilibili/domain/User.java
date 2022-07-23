package com.bilibili.domain;

import lombok.Data;

import java.util.Date;

/**
 * @author Erik_Wong
 * @version 1.0
 * @date 2022/7/20 18:52
 */
@Data
public class User {

    private Long id;

    //手机号
    private String phone;

    //邮箱
    private String email;

    //密码
    private String password;

    //盐值
    private String salt;

    //创建时间
    private Date createTime;

    //更新时间
    private Date updateTime;

    //用户信息
    private UserInfo userInfo;
}
