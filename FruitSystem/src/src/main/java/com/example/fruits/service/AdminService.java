package com.example.fruits.service;

import com.example.fruits.domain.OrderItem;
import com.example.fruits.enums.ExceptionEnum;
import com.example.fruits.mapper.OrderItemMapper;
import com.example.fruits.vo.OrderDetailItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderItemMapper orderItemMapper;

    //待发货信息
    public OrderDetailItem waitDeliver() {
        return orderService.orderWait(false, true, false, false, true, ExceptionEnum.ORDER_WAIT_DELIVER_NOT_FOUND);
    }

    //设置发货
    public Integer setDeliver(Integer oiId) {
       return orderItemMapper.updateByPrimaryKeySelective(new OrderItem().setOiId(oiId).setDeliver(true));
    }

}
