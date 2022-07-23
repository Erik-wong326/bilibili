package com.bilibili.api;

import com.alibaba.fastjson.JSONObject;
import com.bilibili.api.support.UserSupport;
import com.bilibili.domain.JsonResponse;
import com.bilibili.domain.PageResult;
import com.bilibili.domain.User;
import com.bilibili.domain.UserInfo;
import com.bilibili.service.UserFollowingService;
import com.bilibili.service.UserService;
import com.bilibili.service.util.RSAUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Erik_Wong
 * @version 1.0
 * @date 2022/7/20 19:07
 */
@RestController
public class UserApi {

    @Autowired
    private UserService userService;
    
    @Autowired
    private UserSupport userSupport;

    @Autowired
    private UserFollowingService userFollowingService;

    /**
     * 1.获取rsk公钥
     * @return rsa公钥
     */
    @GetMapping("/rsa-pks")
    public JsonResponse<String> getRsaPublicKey(){
        String publicKey = RSAUtil.getPublicKeyStr();
        return new JsonResponse<>(publicKey);
    }

    /**
     * 2.注册用户
     * @param user 用户资料
     * @return 返回注册结果
     */
    @PostMapping(value = "/users")
    public JsonResponse<String> addUser(@RequestBody User user){
        userService.addUser(user);
        return JsonResponse.success();
    }

    /**
     * 3.用户登录
     * @param user 用户资料
     * @return 登录凭证 token
     */
    @PostMapping(value = "/user-token")
    public JsonResponse<String> login(@RequestBody User user) throws Exception{
        String token = userService.login(user);
        return new JsonResponse<>(token);
    }

    /**
     * 4.获取用户信息
     * @return 用户信息
     */
    @GetMapping(value = "/users")
    public JsonResponse<User> getUserInfo(){
        Long currentUserId = userSupport.getCurrentUserId();
        User user = userService.getUserInfo(currentUserId);
        return new JsonResponse<>(user);
    }

    /**
     * 5.更新用户
     * @param user 用户
     * @return 更新结果
     * @throws Exception
     */
    @PutMapping("/users")
    public JsonResponse<String> updateUsers(@RequestBody User user) throws Exception{
        Long userId = userSupport.getCurrentUserId();
        user.setId(userId);
        userService.updateUsers(user);
        return JsonResponse.success();
    }

    /**
     * 5.1更新用户信息
     * @param userInfo 用户信息
     * @return 更新结果
     * @throws Exception
     */
    @PutMapping("/user-infos")
    public JsonResponse<String> updateUserInfos(@RequestBody UserInfo userInfo) throws Exception{
        Long userId = userSupport.getCurrentUserId();
        userInfo.setUserId(userId);
        userService.updateUserInfos(userInfo);
        return JsonResponse.success();
    }

    /**
     * 6.分页查询
     * @param no
     * @param size
     * @param nick
     * @return
     */
    @GetMapping("/user-infos")
    public JsonResponse<PageResult<UserInfo>> pageListUserInfos(@RequestParam Integer no, @RequestParam Integer size, String nick){
        Long userId = userSupport.getCurrentUserId();
        JSONObject params = new JSONObject();
        params.put("no",no);
        params.put("size",size);
        params.put("nick",nick);
        params.put("userId",userId);
        PageResult<UserInfo> result =  userService.pageListUserInfos(params);
        /* 因为当前分页查询是服务于用户关注，因此需要和用户关注产生关联
        所以查出来的用户信息列表，需要判断是否被当前登录的用户关注过
        如果没有关注，才能进行关注，已关注则忽略
        */
        if(result.getTotal() > 0){
            //检查当前用户的关注状态
            List<UserInfo> checkedUserInfoList =  userFollowingService.checkFollowingStatus(result.getList(),userId);
            result.setList(checkedUserInfoList);
        }
        return new JsonResponse<>(result);

    }
}
