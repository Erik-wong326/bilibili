package com.bilibili.service;

import com.bilibili.dao.FollowingGroupDao;
import com.bilibili.domain.FollowingGroup;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Erik_Wong
 * @version 1.0
 * @date 2022/7/22 20:10
 */
public class FollowingGroupService {

    @Autowired
    FollowingGroupDao followingGroupDao;

    public FollowingGroup getByType(String type){
        return followingGroupDao.getByType(type);
    }

    public FollowingGroup getById(Long id){
        return followingGroupDao.getById(id);
    }

    /**
     * 根据 userid 查询其关注分组
     * @param userId
     * @return
     */
    public List<FollowingGroup> getByUserId(Long userId) {
        return followingGroupDao.getByUserId(userId);
    }

    public void addFollowingGroup(FollowingGroup followingGroup) {
        followingGroupDao.addFollowingGroup(followingGroup);
    }

    /**
     * 获取用户关注分组
     * @param userId 用户id
     * @return 关注分组列表
     */
    public List<FollowingGroup> getUserFollowingGroups(Long userId) {
        return followingGroupDao.getUserFollowingGroups(userId);
    }
}
