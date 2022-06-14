package com.example.fruits.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderDetailVo {

    private String oTime;
    private FruitsVo fruits;
    private Double totalMoney;
    private Integer oId;
    private Integer oiId;

}
