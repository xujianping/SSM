package com.xujp.service;

import com.xujp.domain.Gag;

import java.util.List;

/**
 * Created by Xujp on 2018/1/26.
 */
public interface GagService {
    public int insertGag(Gag gag);
    List<Gag> findByUserId(Long id);
}
