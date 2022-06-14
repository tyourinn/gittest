package com.example.fruits.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MyCenterVo extends OrderStatusNum {

    private String userName;
    private String headImage;

    public MyCenterVo(String userName,String headImage,Integer waitPayNum, Integer waitDeliverNum, int waitTakeNum, Integer waitReviewNum) {
        super(waitPayNum,waitDeliverNum,waitTakeNum,waitReviewNum);
        this.userName = userName;
        this.headImage = headImage;
    }

    public MyCenterVo(String userName,String headImage,OrderStatusNum orderStatusNum) {
        super(orderStatusNum);
        this.userName = userName;
        this.headImage = headImage;
    }
}
