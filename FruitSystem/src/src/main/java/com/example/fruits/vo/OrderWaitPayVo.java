package com.example.fruits.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class OrderWaitPayVo extends OrderDetailQuickVo {

    private Long orderExpire;
    private Boolean isPay;
    private Boolean isDeliver;
    private Date oTime;
    private Integer oId;

    public OrderWaitPayVo(boolean isPay) {
        this.isPay = isPay;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    public Date getoTime() {
        return oTime;
    }
}
