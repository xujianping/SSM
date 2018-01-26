package com.xujp.service.Impl;

import com.xujp.dao.GagDao;
import com.xujp.domain.Gag;
import com.xujp.service.GagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Xujp on 2018/1/26.
 */
@Service
public class GagServiceImpl implements GagService {
    @Autowired
    GagDao gagDao;

    @Override
    public int insertGag(Gag gag) {
        return gagDao.insertGag(gag);
    }

    @Override
    public List<Gag> findByUserId(Long id) {
        return gagDao.findByUserId(id);
    }
}
