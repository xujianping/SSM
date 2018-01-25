package com.xujp.service;

import com.xujp.domain.Order;
import com.xujp.domain.OrderExample;

import java.util.List;

/**
 * Created by Xujp on 2018/1/25.
 */
public interface OrderService {
    int insertOrder(Order order);

    int deleteByExample(OrderExample example);

    int updateByPrimaryKeySelective(Order order);

    List<Order> getAllOrdersByExample(OrderExample example);
}
