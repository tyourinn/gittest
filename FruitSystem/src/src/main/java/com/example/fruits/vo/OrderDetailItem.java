package com.example.fruits.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class OrderDetailItem {

    private List<OrderDetailVo> orderDetail;
    private OrderStatusNum orderStatusNum;

}
