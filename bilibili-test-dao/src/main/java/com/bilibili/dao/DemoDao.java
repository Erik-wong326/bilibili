package com.bilibili.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * @author Erik_Wong
 * @version 1.0
 * @date 2022/7/10 18:19
 */
@Mapper
public interface DemoDao {
    public Map<String,Object> query(Long id);
}
