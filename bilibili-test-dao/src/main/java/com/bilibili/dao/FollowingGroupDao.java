package com.bilibili.dao;

import com.bilibili.domain.FollowingGroup;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Erik_Wong
 * @version 1.0
 * @date 2022/7/22 20:08
 */
@Mapper
public interface FollowingGroupDao {


    /**
     * 1.根据类型查询关注分组
     * @param type 类型
     * @return 查询的关注分组结果
     */
    FollowingGroup getByType(String type);

    /**
     * 2.根据id查询关注分组
     * @param id id
     * @return 查询的关注分组结果
     */
    FollowingGroup getById(Long id);

    /**
     * 3. 根据userid查询该用户的所有关注分组
     * @param userId 用户id
     * @return 所有关注分组
     */
    List<FollowingGroup> getByUserId(Long userId);

    /**
     * 4.新建用户自定义的关注分组
     * @param followingGroup 关注分组
     * @return 新增结果
     */
    Integer addFollowingGroup(FollowingGroup followingGroup);

    /**
     * 5.获取用户关注分组
     * @param userId 用户id
     * @return 关注分组列表
     */
    List<FollowingGroup> getUserFollowingGroups(Long userId);
}
