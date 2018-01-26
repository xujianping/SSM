package com.xujp.controller;


import com.xujp.domain.Gag;
import com.xujp.domain.Score;
import com.xujp.service.GagService;
import com.xujp.service.JedisClient;
import com.xujp.service.ScoreService;
import com.xujp.utils.BaseResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Xujp on 2018/1/26.
 */
@RestController
@RequestMapping("/test")
public class TestController {
    private static final Logger log = LogManager.getLogger(OrderController.class);

    @Autowired
    private ScoreService scoreService;
    @Autowired
    private GagService gagService;
    @Autowired
    private JedisClient jedisClient;


    @RequestMapping(value = "/testMvc",produces="text/html;charset=UTF-8", method = {RequestMethod.GET, RequestMethod.GET})
    public BaseResult test() {
        BaseResult result = new BaseResult();
        Score score = new Score();
        score.setChangeType("玩游戏");
        score.setScore(10);
        int isSucc = scoreService.insertScore(score);
        if (isSucc == 1){
            result.setSuccess(true);
            result.setData(score);
            result.setMsg("插入成功");
        }else {
            result.setSuccess(false);
            result.setMsg("失败");
        }

        return result;
    }


//    @RequestMapping(value = "/testRedis",produces="text/html;charset=UTF-8", method = {RequestMethod.GET,RequestMethod.GET})
//    public String testRedis(Long id){
//        List<Gag> gagList= null;
//        try {
//            String resulthget = jedisClient.hget("禁言表", id + "");
//            if (resulthget != null) {
//                //字符串转为list
//                System.out.println("有缓存啦啦啦！！！");
//                JSONArray array = JSONArray.parseArray(resulthget);
//                gagList = (List) array;
//            } else {
//                System.out.println("禁言表没查过");
//                gagList= gagService.findByUserId(id);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        try {
//            String cacheString = JSON.toJSONString(gagList);
//            jedisClient.hset("禁言表", id + "", cacheString);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return JSON.toJSONString(gagList);
//    }
}
