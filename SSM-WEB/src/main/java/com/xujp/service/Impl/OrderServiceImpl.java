package com.xujp.service.Impl;

import com.xujp.dao.OrderDao;
import com.xujp.domain.Order;
import com.xujp.domain.OrderExample;
import com.xujp.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Created by Xujp on 2018/1/25.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Override
    public int insertOrder(Order order) {
        return orderDao.insert(order);
    }

    @Override
    public int deleteByExample(OrderExample example) {
        return orderDao.deleteByExample(example);
    }

    @Override
    public int updateByPrimaryKeySelective(Order order) {
        return orderDao.updateByPrimaryKeySelective(order);
    }

    @Override
    public List<Order> getAllOrdersByExample(OrderExample example) {
        return orderDao.selectByExample(example);
    }

}
