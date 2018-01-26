package com.xujp.controller;


import com.xujp.domain.Gag;
import com.xujp.domain.Score;
import com.xujp.service.GagService;
import com.xujp.service.JedisClient;
import com.xujp.service.ScoreService;
import com.xujp.utils.BaseResult;
import com.xujp.utils.JacksonUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Xujp on 2018/1/26.
 */
@Controller
@RequestMapping("/test")
public class TestController {
    private static final Logger log = LogManager.getLogger(OrderController.class);

    @Autowired
    private ScoreService scoreService;
    @Autowired
    private GagService gagService;
    @Autowired
    private JedisClient jedisClient;

    @RequestMapping(value = "/testMvc",produces="application/json;charset=UTF-8", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
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


    @RequestMapping(value = "/testRedis", method = {RequestMethod.GET,RequestMethod.GET})
    public BaseResult testRedis(Long id){
        BaseResult result = new BaseResult();
        List<Gag> gagList= null;
        try {
            String resulthget = jedisClient.hget("禁言表", id + "");
            if (resulthget != null) {
                //字符串转为list
                System.out.println("有缓存啦啦啦！！！");
//                gagList = JacksonUtil.json2list(resulthget,Gag);
//                JSONArray array = JSONArray.parseArray(resulthget);
//                gagList = (List) array;
            } else {
                System.out.println("禁言表没查过");
                gagList= gagService.findByUserId(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            jedisClient.hset("禁言表", id + "", gagList.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.setData(gagList);
        return result;
    }
}
