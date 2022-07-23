package com.bilibili.domain;

import lombok.Data;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.util.List;

/**
 * @author Erik_Wong
 * @version 1.0
 * @date 2022/7/23 16:50
 */
@Data
public class PageResult <T>{

    //分页查询结果总数
    private Integer total;

    //分页查询当前列表
    private List<T> list;

    public PageResult(Integer total,List<T> list){
        this.total = total;
        this.list = list;
    }
}
