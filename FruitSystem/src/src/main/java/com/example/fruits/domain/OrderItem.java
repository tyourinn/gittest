package com.example.fruits.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

@Data
@NoArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer oiId;
    private Integer oId;
    private Integer fId;
    private String title;
    private Integer price;
    private Integer num;
    private Boolean isReview;
    private Boolean isDeliver;
    private Boolean isTake;

    public OrderItem setDeliver(Boolean deliver) {
        isDeliver = deliver;
        return this;
    }

    public OrderItem setReview(Boolean review) {
        isReview = review;
        return this;
    }

    public OrderItem(boolean isDeliver, Integer oId, boolean isTake) {
        this(isDeliver,oId);
        this.isTake = isTake;
    }

    public OrderItem(boolean isDeliver, Integer oId, boolean isTake,boolean isReview) {
        this(isDeliver,oId);
        this.isTake = isTake;
        this.isReview = isReview;
    }

    public OrderItem setTake(Boolean take) {
        isTake = take;
        return this;
    }

    public OrderItem setOiId(Integer oiId) {
        this.oiId = oiId;
        return this;
    }

    @Transient
    private Fruits fruits;

    public OrderItem(Integer oId) {
        this.oId = oId;
    }

    public OrderItem(Integer oId, boolean isReview) {
        this.oId = oId;
        this.isReview = isReview;
    }

    public OrderItem(boolean isDeliver, Integer oId) {
        this.isDeliver = isDeliver;
        this.oId = oId;
    }

    public OrderItem(Integer oId, boolean isDeliver, boolean isReview) {
        this(isDeliver,oId);
        this.isReview = isReview;
    }
}
