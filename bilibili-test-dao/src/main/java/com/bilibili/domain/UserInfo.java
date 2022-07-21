package com.bilibili.domain;

import lombok.Data;

import java.util.Date;

/**
 * @author Erik_Wong
 * @version 1.0
 * @date 2022/7/20 19:02
 */
@Data
public class UserInfo {

    private Long id;

    private Long userId;

    private String nick;

    private String avatar;

    private String sign;

    private String gender;

    private String birth;

    private Date createTime;

    private Date updateTime;

}
