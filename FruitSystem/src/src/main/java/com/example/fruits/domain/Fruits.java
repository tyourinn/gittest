package com.example.fruits.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.List;

@Data
@NoArgsConstructor
public class Fruits {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer fId;
    private String title;
    private String specification;
    private String supplier;
    @JsonIgnore
    private Integer price;
    @JsonIgnore
    private Integer priceOff;
    private String placeOrigin;
    private String image;

    @Transient
    private String priceStr;
    @Transient
    private String priceOffStr;
    @Transient
    private List<FruitsImages> fruitsImages;
    @Transient
    private List<FruitsImageDetails> fruitsImageDetails;
    @Transient
    private List<Review> reviews;
    @Transient
    private Integer shopCartNum;

    public Fruits(String search) {
        this.title = search;
    }

    public String getPriceStr() {
        return price/10.0+"";
    }

    public String getPriceOffStr() {
        return priceOff/10.0+"";
    }


}
