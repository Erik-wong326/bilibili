package com.bilibili.service.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.bilibili.exception.ConditionException;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Erik_Wong
 * @version 1.0
 * @date 2022/7/21 7:16
 */
public class TokenUtil {

    private static final String ISSUER = "签发人";

    /**
     * 1.登录令牌 token
     * @param userId 用户id
     * @return 返回String类
     * @throws Exception
     */
    public static String generateToken(Long userId) throws Exception {
        //1.构建基于RSA256的算法
        Algorithm algorithm = Algorithm.RSA256(RSAUtil.getPublicKey(), RSAUtil.getPrivateKey());
        //2.calendar 用于构造过期时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());//初始时间
        calendar.add(Calendar.SECOND, 30); // 过期时间
        //3.构造JWT并返回
        return JWT.create().withKeyId(String.valueOf(userId))
                .withIssuer(ISSUER)
                .withExpiresAt(calendar.getTime())
                .sign(algorithm);
    }

    /**
     * 2.验证用户登录令牌
     * @param token 令牌
     * @return 验证结果
     */
    public static Long verifyToken(String token){
        try {
            Algorithm algorithm = Algorithm.RSA256(RSAUtil.getPublicKey(), RSAUtil.getPrivateKey());
            //根据 RSA 算法构造一个验证类 verifier
            JWTVerifier verifier = JWT.require(algorithm).build();
            //验证类 verifier 验证 token -> 得到解码后的jwt
            DecodedJWT jwt = verifier.verify(token);
            //取出 userId
            String userId = jwt.getKeyId();
            return Long.valueOf(userId);
        } catch (TokenExpiredException e) {
            //情况1:token过期
            throw new ConditionException("555","token过期!");
        } catch (Exception e){
            throw new ConditionException("非发用户token!");
        }
    }

}
