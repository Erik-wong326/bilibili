package com.bilibili.dao;

import com.bilibili.domain.User;
import com.bilibili.domain.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author Erik_Wong
 * @version 1.0
 * @date 2022/7/20 19:09
 */
@Mapper
public interface UserDao {



    /**
     * 2.用户注册
     * @param user 用户资料
     * @return 用户注册结果
     */
    Integer addUser(User user);


    /**
     * 2.1 用户注册，添加用户信息
     * @param userInfo 用户信息
     * @return 添加成功与否
     */
    Integer addUserInfo(UserInfo userInfo);

    /**
     * 2.2 根据手机号，获取用户信息
     * @param phone 手机号
     * @return 用户
     */
    User getUserByPhone(String phone);

    /**
     * 3. 根据 phone 或 email 获取用户
     * @param phone 手机号码
     * @param email 电子邮箱
     * @return 用户
     */
    User getUserByPhoneOrEmail(String phone, String email);

    /**
     * 4.根据id获取user
     * @param id 用户id
     * @return User
     */
    User getUserById(Long id);

    /**
     * 4.1 根据userId获取userInfo
     * @param userId 用户id
     * @return userInfo
     */
    UserInfo getUserInfoByUserId(Long userId);

    /**
     * 5 更新用户
     * @param user 用户对象
     * @return 更新结果
     */
    Integer updateUsers(User user);

    /**
     * 5.1 更新用户信息
     * @param userInfo 用户信息对象
     * @return 更新结果
     */
    Integer updateUserInfos(UserInfo userInfo);

}
