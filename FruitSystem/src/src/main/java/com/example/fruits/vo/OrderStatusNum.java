package com.example.fruits.vo;

import lombok.Data;

@Data
public class OrderStatusNum {

    private Integer waitPay;
    private Integer waitDeliver;
    private Integer waitTake;
    private Integer waitReview;

    public OrderStatusNum() {
    }

    public OrderStatusNum(Integer waitPay, Integer waitDeliver, Integer waitTake, Integer waitReview) {
        this.waitPay = waitPay;
        this.waitDeliver = waitDeliver;
        this.waitTake = waitTake;
        this.waitReview = waitReview;
    }

    public OrderStatusNum(OrderStatusNum orderStatusNum) {
        this.waitPay = orderStatusNum.getWaitPay();
        this.waitDeliver = orderStatusNum.getWaitDeliver();
        this.waitTake = orderStatusNum.getWaitTake();
        this.waitReview = orderStatusNum.getWaitReview();
    }
}
