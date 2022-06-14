package com.example.fruits.vo;

import com.example.fruits.domain.Fruits;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FruitsVo {

    private Integer fId;
    private String image;
    private String title;
    private Double price;
    private Integer num;

    public FruitsVo(Fruits fruits,Integer num) {
        this.setFId(fruits.getFId());
        this.setImage(fruits.getImage());
        this.setTitle(fruits.getTitle()+" " +fruits.getSpecification());
        this.setPrice(fruits.getPriceOff()/10.0);
        this.num = num;
    }


}
