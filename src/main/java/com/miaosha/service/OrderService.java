package com.miaosha.service;

import com.miaosha.error.BusinessException;
import com.miaosha.service.model.OrderModel;

public interface OrderService {

    //创建订单
    OrderModel createOrder(Integer userId, Integer itemId, Integer amount) throws BusinessException;
}
