package com.bilibili.service;

import com.bilibili.dao.FollowingGroupDao;
import com.bilibili.dao.UserFollowingDao;
import com.bilibili.domain.FollowingGroup;
import com.bilibili.domain.User;
import com.bilibili.domain.UserFollowing;
import com.bilibili.domain.UserInfo;
import com.bilibili.domain.constant.UserConstant;
import com.bilibili.exception.ConditionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Erik_Wong
 * @version 1.0
 * @date 2022/7/22 20:09
 */
@Service
public class UserFollowingService {

    @Autowired
    UserFollowingDao userFollowingDao;

    @Autowired
    FollowingGroupService followingGroupService;

    @Autowired
    UserService userService;

    /**
     * 1.添加关注用户
     * @param userFollowing
     */
    @Transactional
    public void addUserFollowings(UserFollowing userFollowing){
        //开始添加用户时,如果不做选择都是放到默认分组
        //1.因此要判断 groupId
        Long groupId = userFollowing.getGroupId();
        if (groupId == null){
            //case1:分组id不存在,放进默认分组 type:2
            FollowingGroup followingGroup = followingGroupService.getByType(UserConstant.USER_FOLLOWING_GROUP_TYPE_DEFAULT);
            userFollowing.setGroupId(followingGroup.getId());
        }else {
            //case2:默认用户选择了具体的分组
            FollowingGroup followingGroup = followingGroupService.getById(groupId);
            if (followingGroup == null){
                throw new ConditionException("关注的分组不存在");
            }
        }
        //2.判断关注的up主是否存在
        Long followingId = userFollowing.getFollowingId();
        User user = userService.getUserById(followingId);
        if (user == null){
            throw new ConditionException("关注的用户不存在!");
        }
        //3.新增
        //3.1 我和up主的关联时，先判断关联关系是否存在,存在则删除后再新增
        userFollowingDao.deleteUserFollowing(userFollowing.getUserId(), followingId);
        //3.2 新增
        userFollowing.setCreateTime(new Date());
        userFollowingDao.addUserFollowing(userFollowing);
    }
    


    /**
     * 2. 获取用户关注列表
     * step1.获取关注用户的列表
     * step2.根据关注用户的id查询关注用户的基本信息
     * step3.将关注用户按关注分组进行分类
     * @param userId 用户id
     * @return 关注列表
     */
    public List<FollowingGroup> getUserFollowings(Long userId){
        //1.用户关注的列表
        List<UserFollowing> list = userFollowingDao.getUserFollowings(userId);
        //2.根据关注用户的id，查询关注用户的基本信息
        Set<Long> followingIdSet = list.stream().map(UserFollowing::getFollowingId).collect(Collectors.toSet());
        List<UserInfo> userInfoList = new ArrayList<>();
        if (followingIdSet.size() > 0){
            userInfoList = userService.getUserInfoByUserIds(followingIdSet);
        }
        for (UserFollowing userFollowing : list) {
            for (UserInfo userInfo : userInfoList) {
                if (userFollowing.getFollowingId().equals(userInfo.getUserId())) {
                    userFollowing.setUserInfo(userInfo);
                }
            }
        }
        //3.将关注用户按关注分组进行分类
        List<FollowingGroup> groupList = followingGroupService.getByUserId(userId);
        //全部关注的分组
        FollowingGroup allGroup = new FollowingGroup();
        allGroup.setName(UserConstant.USER_FOLLOWING_GROUP_ALL_NAME);
        allGroup.setFollowingUserInfoList(userInfoList);
        List<FollowingGroup> result = new ArrayList<>();
        result.add(allGroup);
        //groupList 和 userInfo 的整合
        for (FollowingGroup group : groupList) {
            List<UserInfo> infoList = new ArrayList<>();
            for (UserFollowing userFollowing : list) {
                if (group.getId().equals(userFollowing.getUserId())) {
                    infoList.add(userFollowing.getUserInfo());
                }
            }
            group.setFollowingUserInfoList(infoList);
            result.add(group);
        }
        return result;
    }

    /**
     * 3. 获取用户粉丝列表
     * step1:获取当前用户的粉丝列表
     * step2:根据粉丝的用户id查询基本信息
     * step3:查询当前用户是否已经关注该粉丝 - 互粉
     * @param userId 用户id
     * @return 用户粉丝列表
     */
    public List<UserFollowing> getUserFans(Long userId){
        //1.获取粉丝列表
        List<UserFollowing> fansList = userFollowingDao.getUserFans(userId);
        //2.根据粉丝id查询基本信息
        //抽取粉丝的所有id
        Set<Long> fanIdSet = fansList.stream().map(UserFollowing::getUserId).collect(Collectors.toSet());
        List<UserInfo> userInfoList = new ArrayList<>();
        if (fanIdSet.size() > 0){
            userInfoList = userService.getUserInfoByUserIds(fanIdSet);
        }
        //3.查询是否互粉
        //获取当前用户的关注列表
        List<UserFollowing> followingList = userFollowingDao.getUserFollowings(userId);
        for (UserFollowing fans : fansList) {
            for (UserInfo userInfo : userInfoList) {
                if (fans.getUserId().equals(userInfo.getUserId())){
                    //互粉初始化,粉丝状态信息赋值
                    userInfo.setFollowed(false);
                    fans.setUserInfo(userInfo);
                }
            }
            for (UserFollowing following : followingList) {
                if (fans.getUserId().equals(following.getFollowingId())){
                    fans.getUserInfo().setFollowed(true);
                }
            }
        }
        return fansList;
        
    }

    /**
     * 4.新增用户自定义关注分组
     * @param followingGroup 自定义关注分组
     * @return 新增结果
     */
    public Long addUserFollowingGroups(FollowingGroup followingGroup) {
        followingGroup.setCreateTime(new Date());
        followingGroup.setType(UserConstant.USER_FOLLOWING_GROUP_TYPE_USER);
        followingGroupService.addFollowingGroup(followingGroup);
        return followingGroup.getId();
    }

    /**
     * 5. 获取用户关注分组
     * @param userId 用户id
     * @return 关注分组列表
     */
    public List<FollowingGroup> getUserFollowingGroups(Long userId) {
        return followingGroupService.getUserFollowingGroups(userId);
    }

    /**
     * 6. 检查当前用户的关注状态
     * @param userInfoList 用户状态列表
     * @param userId 用户id
     * @return
     */
    public List<UserInfo> checkFollowingStatus(List<UserInfo> userInfoList, Long userId) {
        //1.查询当前登录的用户，已经关注了哪些用户
        List<UserFollowing> userFollowingList = userFollowingDao.getUserFollowings(userId);
        for (UserInfo userInfo : userInfoList) {
            //初始化关注状态
            userInfo.setFollowed(false);
            for (UserFollowing userFollowing : userFollowingList) {
                //following的id和UserInfo的UserId一致，则更新状态
                if (userFollowing.getFollowingId().equals(userInfo.getUserId())){
                    userInfo.setFollowed(true);
                }
            }
        }
        //返回更新完关注状态的userInfoList
        return userInfoList;
    }
}
