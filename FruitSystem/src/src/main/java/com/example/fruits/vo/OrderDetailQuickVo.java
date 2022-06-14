package com.example.fruits.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class OrderDetailQuickVo {

    private AddressVo address;
    private List<FruitsVo> fruits;
    private Double totalMoney;

    public OrderDetailQuickVo(AddressVo address, List<FruitsVo> fruits, Double totalMoney) {
        this.address = address;
        this.fruits = fruits;
        this.totalMoney = totalMoney;
    }
}
