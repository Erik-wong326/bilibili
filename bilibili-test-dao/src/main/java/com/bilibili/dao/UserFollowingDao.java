package com.bilibili.dao;

import com.bilibili.domain.UserFollowing;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Erik_Wong
 * @version 1.0
 * @date 2022/7/22 20:09
 */
@Mapper
public interface UserFollowingDao {

    /**
     * 1. 删除用户和up的关注关系
     * @param userId 用户
     * @param followingId 关注人的id
     * @return 删除结果
     */
    Integer deleteUserFollowing(@Param("userId") Long userId,@Param("followingId") Long followingId);

    /**
     * 2. 新增用户和up的关注关系
     * @param userFollowing 用户关注关系
     * @return 新增结果
     */
    Integer addUserFollowing(UserFollowing userFollowing);

    /**
     * 3. 获取用户关注列表
     * @param userId 用户id
     * @return 用户关注列表
     */
    List<UserFollowing> getUserFollowings(Long userId);

    /**
     * 4. 获取用户粉丝
     * @param userId 用户id
     * @return 用户粉丝列表
     */
    List<UserFollowing> getUserFans(Long userId);
}
