package com.bilibili.api;

import com.alibaba.fastjson.JSONObject;
import com.bilibili.api.support.UserSupport;
import com.bilibili.domain.*;
import com.bilibili.service.FollowingGroupService;
import com.bilibili.service.UserFollowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author Erik_Wong
 * @version 1.0
 * @date 2022/7/23 16:10
 */
public class UserFollowingApi {

    @Autowired
    private UserFollowingService userFollowingService;

    @Autowired
    private UserSupport userSupport;

    /**
     * 1. 添加关注用户
     * @param userFollowing 关注的用户
     * @return 关注成功
     */
    @PostMapping("/user-followings")
    public JsonResponse<String> addUserFollowings(@RequestBody UserFollowing userFollowing){
        Long userId = userSupport.getCurrentUserId();
        userFollowing.setUserId(userId);
        userFollowingService.addUserFollowings(userFollowing);
        return JsonResponse.success();
    }

    /**
     * 2. 获取用户关注列表
     * @return
     */
    @GetMapping("/user-followings")
    public JsonResponse<List<FollowingGroup>> getUserFollowings(){
        Long userId = userSupport.getCurrentUserId();
        List<FollowingGroup> result = userFollowingService.getUserFollowings(userId);
        return new JsonResponse<>(result);
    }

    /**
     * 3. 获取用户粉丝列表
     * @return 粉丝列表
     */
    @GetMapping("/user-fans")
    public JsonResponse<List<UserFollowing>> getUserFans(){
        Long userId = userSupport.getCurrentUserId();
        List<UserFollowing> result = userFollowingService.getUserFans(userId);
        return new JsonResponse<>(result);
    }

    /**
     * 4.新建用户自定义的关注分组
     * @param followingGroup 自定义关注分组
     * @return 新增结果
     */
    @PostMapping("/user-following-groups")
    public JsonResponse<Long> addUserFollowingGroups(@RequestBody FollowingGroup followingGroup){
        Long userId = userSupport.getCurrentUserId();
        followingGroup.setUserId(userId);
        Long groupId = userFollowingService.addUserFollowingGroups(followingGroup);
        return new JsonResponse<>(groupId);
    }

    /**
     * 5.获取用户关注分组
     * @return
     */
    @GetMapping("/user-following-groups")
    public JsonResponse<List<FollowingGroup>> getUserFollowingGroups(){
        Long userId = userSupport.getCurrentUserId();
        List<FollowingGroup> list = userFollowingService.getUserFollowingGroups(userId);
        return new JsonResponse<>(list);
    }
}
