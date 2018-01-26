package com.xujp.dao;

import com.xujp.domain.Score;

/**
 * Created by Xujp on 2018/1/26.
 */
public interface ScoreDao {
    //插入积分记录
    int insertScore(Score score);
}
