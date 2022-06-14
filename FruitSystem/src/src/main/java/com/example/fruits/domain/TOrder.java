package com.example.fruits.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class TOrder {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer oId;
    private Integer uId;
    private Integer addId;
    private Boolean isPlay;
    private Timestamp oTime;
    private Integer totalMoney;
    private String number;

    public TOrder(Integer addId, String out_trade_no, Integer totalMoney,Integer uId) {
        this.addId = addId;
        this.number = out_trade_no;
        this.totalMoney = totalMoney;
        this.uId = uId;
    }

    public TOrder(Integer oId,Integer uId, boolean isPlay) {
        this.oId = oId;
        this.isPlay = isPlay;
        this.uId = uId;
    }

    public TOrder(Integer oId, Integer uId) {
        this.oId = oId;
        this.uId = uId;
    }

    public TOrder(Integer uId, boolean isPay) {
        this.uId = uId;
        this.isPlay = isPay;
    }

    public TOrder(Integer uId) {
        this.uId = uId;
    }

    public TOrder(boolean isPay) {
        this.isPlay = isPay;
    }
}
