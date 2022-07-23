package com.bilibili.domain;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author Erik_Wong
 * @version 1.0
 * @date 2022/7/22 20:06
 */
@Data
public class FollowingGroup {

    private Long id;

    //用户id
    private Long userId;

    //关注分组名称
    private String name;

    //关注分组类型：0特别关注  1悄悄关注 2默认分组  3用户自定义分组
    private String type;

    //创建时间
    private Date createTime;

    //更新时间
    private Date updateTime;

    //关注的用户的信息
    private List<UserInfo> followingUserInfoList;

}
