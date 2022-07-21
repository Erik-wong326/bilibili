package com.bilibili.service;

import com.bilibili.dao.UserDao;
import com.bilibili.domain.User;
import com.bilibili.domain.UserInfo;
import com.bilibili.domain.constant.UserConstant;
import com.bilibili.exception.ConditionException;
import com.bilibili.service.util.MD5Util;
import com.bilibili.service.util.RSAUtil;
import com.bilibili.service.util.TokenUtil;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Erik_Wong
 * @version 1.0
 * @date 2022/7/20 19:06
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public void addUser(User user) {
        String phone = user.getPhone();
        //1.判断手机号是否合法
        if (StringUtils.isNullOrEmpty(phone)) {
            throw new ConditionException("手机号不能为空");
        }
        //2.判断手机号是否已被注册过
        User dbUser = this.getUserByPhone(phone);
        if (dbUser != null){
            throw new ConditionException("该手机号已经注册");
        }
        //3.注册逻辑：添加 user 信息到 User 表
        //3.1密码RSA解密,MD5加密处理
        //获取时间戳，进行md5加密
        Date now = new Date();
        //用时间戳，作为盐值
        String salt = String.valueOf(now.getTime());
        //password 在前端进行了 RSA 加密，这里需要解密
        String password = user.getPassword();
        //rawPassword:前端传过来的密码，解密后的明文密码
        String rawPassword;
        try {
            rawPassword = RSAUtil.decrypt(password);
        } catch (Exception e) {
            throw new ConditionException("密码解密失败!");
        }
        String md5Password = MD5Util.sign(rawPassword, salt, "UTF-8");
        //3.2设置user各个字段
        user.setSalt(salt);
        user.setPassword(md5Password);
        user.setCreateTime(now);
        userDao.addUser(user);
        //4.添加用户信息到 UserInfo 表
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(user.getId());
        userInfo.setNick(UserConstant.DEFAULT_NICK);
        userInfo.setBirth(UserConstant.DEFAULT_BIRTH);
        userInfo.setGender(UserConstant.GENDER_MAEL);
        userInfo.setCreateTime(now);
        userDao.addUserInfo(userInfo);

    }

    public User getUserByPhone(String phone){
        return userDao.getUserByPhone(phone);
    }

    public String login(User user) {
        //1.判断手机号
        String phone = user.getPhone();
        if (StringUtils.isNullOrEmpty(phone)) {
            throw new ConditionException("手机号不能为空");
        }
        //2.判断用户是否在数据库中存在
        User dbUser = this.getUserByPhone(phone);
        if (null == dbUser){
            throw new ConditionException("当前用户不存在");
        }
        //3.判断用户密码是否匹配
        String password = user.getPassword();
        //3.1. 对前端传过来的加密过的密码进行RSA解密
        String rawPassword = null;
        try {
            rawPassword = RSAUtil.decrypt(password);
        } catch (Exception e) {
            throw new ConditionException("密码解密失败!");
        }
        //3.2 获取salt,将 rawPassword 和 salt 组合,与数据库的密码比对
        String salt = dbUser.getSalt();
        String md5Password = MD5Util.sign(rawPassword,salt,"UTF-8");
        //比对数据库中 user 的密码
        if (!md5Password.equals(dbUser.getPassword())){
            throw new ConditionException("密码错误!");
        }
        //返回token
        TokenUtil tokenUtil = new TokenUtil();
        return tokenUtil.generateToken(dbUser.getId());
    }
}
