package com.example.fruits.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class OrderStatusItem {

    private List<OrderWaitPayVo> orderWaitPayVoList;
    private OrderStatusNum orderStatusNum;


    public OrderStatusItem(List<OrderWaitPayVo> resultList, OrderStatusNum orderStatusNum) {
        this.orderWaitPayVoList = resultList;
        this.orderStatusNum = orderStatusNum;
    }
}
