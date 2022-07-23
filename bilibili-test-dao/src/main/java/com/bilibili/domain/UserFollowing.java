package com.bilibili.domain;

import lombok.Data;

import java.util.Date;

/**
 * @author Erik_Wong
 * @version 1.0
 * @date 2022/7/22 20:04
 */
@Data
public class UserFollowing {

    private Long id;

    //用户id
    private Long userId;

    //关注用户id
    private Long followingId;

    //关注分组id
    private Long groupId;

    //创建时间
    private Date createTime;

    //用户信息
    private UserInfo userInfo;
}
