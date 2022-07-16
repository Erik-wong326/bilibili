package com.bilibili;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * @author Erik_Wong
 * @version 1.0
 * @date 2022/7/10 17:53
 */
@SpringBootApplication
public class BilibiliApp {
    public static void main(String[] args){
        ApplicationContext app = SpringApplication.run(BilibiliApp.class,args);
    }
}
