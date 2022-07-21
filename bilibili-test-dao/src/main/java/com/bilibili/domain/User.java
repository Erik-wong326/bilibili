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

    private String phone;

    private String email;

    private String password;

    private String salt;

    private Date createTime;

    private Date updateTime;

}
