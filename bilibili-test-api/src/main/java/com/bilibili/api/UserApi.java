package com.bilibili.api;

import com.bilibili.domain.JsonResponse;
import com.bilibili.domain.User;
import com.bilibili.service.UserService;
import com.bilibili.service.util.RSAUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Erik_Wong
 * @version 1.0
 * @date 2022/7/20 19:07
 */
@RestController
public class UserApi {

    @Autowired
    private UserService userService;

    @GetMapping("/rsa-pks")
    public JsonResponse<String> getRsaPublicKey(){
        String publicKey = RSAUtil.getPublicKeyStr();
        return new JsonResponse<>(publicKey);
    }

    @PostMapping(value = "/users")
    public JsonResponse<String> addUser(@RequestBody User user){
        userService.addUser(user);
        return JsonResponse.success();
    }

    @PostMapping(value = "/user-token")
    public JsonResponse<String> login(@RequestBody User user){
        String token = userService.login(user);
        return new JsonResponse<>(token);
    }

}