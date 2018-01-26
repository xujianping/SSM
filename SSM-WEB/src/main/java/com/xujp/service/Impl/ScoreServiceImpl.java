package com.xujp.service.Impl;

import com.xujp.dao.ScoreDao;
import com.xujp.domain.Score;
import com.xujp.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Xujp on 2018/1/26.
 */
@Service
public class ScoreServiceImpl implements ScoreService {
    @Autowired
    private ScoreDao scoreDao;

    @Override
    public int insertScore(Score score) {
        int res = scoreDao.insertScore(score);
        return res;
    }
}
