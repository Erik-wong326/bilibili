package com.bilibili.service;

import com.bilibili.dao.DemoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Erik_Wong
 * @version 1.0
 * @date 2022/7/10 18:26
 */
@Service
public class DemoService {
    @Autowired
    private DemoDao demoDao;

    public Map<String,Object> query(Long id){
        return demoDao.query(id);
    }
}
