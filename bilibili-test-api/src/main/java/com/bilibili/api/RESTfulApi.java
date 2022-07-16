package com.bilibili.api;

import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Erik_Wong
 * @version 1.0
 * @date 2022/7/16 15:38
 */

@RestController
public class RESTfulApi {
    public final Map<Integer, Map<String,Object>> dataMap;
    
    public RESTfulApi(){
        dataMap = new HashMap<>();
        //给dataMap造数据
        for (int i = 1; i < 3; i++) {
            Map<String,Object> data = new HashMap<>();
            data.put("id",i);
            data.put("name","mark" + i);
            dataMap.put(i,data);
        }
    }

    @GetMapping(value = "/objects/{id}")
    public Map<String,Object> getData(@PathVariable Integer id){
        return dataMap.get(id);
    }

    @DeleteMapping(value = "/objects/{id}")
    public String deleteData(@PathVariable Integer id){
        dataMap.remove(id);
        return "DELETE SUCCESS";
    }

    @PostMapping(value = "/objects")
    public String postData(@RequestBody Map<String,Object> data){
        //获取当前 keySet 并转化为 idArray
        Integer[] idArray = dataMap.keySet().toArray(new Integer[0]);
        Arrays.sort(idArray);
        //造下一个 id
        int nextId = idArray[idArray.length-1] + 1;
        dataMap.put(nextId,data);
        return "post Success";
    }

    @PutMapping(value = "/objects")
    public String putData(@RequestBody Map<String,Object> data){
        //获取data的id
        Integer id = Integer.valueOf(String.valueOf(data.get("id")));
        //获取data的id在 dataMap 中是否存在
        Map<String,Object> containsData = dataMap.get(id);
        //判断data传进来的id,在 dataMap 中是否已经存在?
        if (null == containsData){
            //不存在，则 put 新增数据
            //获取当前 keySet 并转化为 idArray
            Integer[] idArray = dataMap.keySet().toArray(new Integer[0]);
            Arrays.sort(idArray);
            //造下一个 id
            int nextId = idArray[idArray.length-1] + 1;
            dataMap.put(nextId,data);
        }else {
            //存在,则 put 修改数据
            dataMap.put(id,data);
        }
        return "put success";
    }
}
