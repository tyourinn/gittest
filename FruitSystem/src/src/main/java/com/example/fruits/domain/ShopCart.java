package com.example.fruits.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@NoArgsConstructor
public class ShopCart {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer scId;
    private Integer uId;
    private Integer fId;
    private Integer num;
    private Fruits fruits;

    public ShopCart(Integer uId, Integer fId, Integer num) {
        this.uId = uId;
        this.fId = fId;
        this.num = num;
    }

    public ShopCart(Integer uId) {
        this.uId = uId;
    }

    public ShopCart(Integer uId, Integer fId) {
        this.uId = uId;
        this.fId = fId;
    }

    @JsonIgnore
    public Integer getuId() {
        return uId;
    }

    @JsonIgnore
    public Integer getfId() {
        return fId;
    }
}
