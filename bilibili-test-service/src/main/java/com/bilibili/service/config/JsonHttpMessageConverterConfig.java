package com.bilibili.service.config;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Erik_Wong
 * @version 1.0
 * @date 2022/7/16 16:34
 */
@Configuration
public class JsonHttpMessageConverterConfig {

    /**
     * 循环引用代码示例
     * @return
     */
    public static void main(String[] args){
        List<Object> list = new ArrayList<>();
        Object o = new Object();
        list.add(o);
        list.add(o);
        System.out.println(list.size());
        System.out.println(JSONObject.toJSONString(list));
        System.out.println(JSONObject.toJSONString(list,SerializerFeature.DisableCircularReferenceDetect));

    }

    @Bean
    @Primary
    public HttpMessageConverters fastJsonHttpMessageConverters(){
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        //配置fastJsonConfig的日期格式
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
        //配置fastJsonConfig的日期格式
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.PrettyFormat,//json格式化输出
                SerializerFeature.WriteNullStringAsEmpty, //返回前端的json数据中,值为null 则置为空
                SerializerFeature.WriteNullListAsEmpty, //返回前端的json,list数据中,值为null 则置为空
                SerializerFeature.WriteMapNullValue, //Map中为null的值,置为空
                SerializerFeature.MapSortField, //对json中map的数据根据key进行升序排序
                SerializerFeature.DisableCircularReferenceDetect //检测并禁用循环引用
        );
        fastConverter.setFastJsonConfig(fastJsonConfig);
        return new HttpMessageConverters(fastConverter);
    }
}
