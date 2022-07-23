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

    //用户id
    private Long userId;

    //昵称
    private String nick;

    //头像
    private String avatar;

    //签名
    private String sign;

    //性别：0男 1女 2未知'
    private String gender;

    //生日
    private String birth;

    //创建时间
    private Date createTime;

    //更新时间
    private Date updateTime;

    //互粉状态 true 已互粉 false 未互粉
    private Boolean followed;

}
