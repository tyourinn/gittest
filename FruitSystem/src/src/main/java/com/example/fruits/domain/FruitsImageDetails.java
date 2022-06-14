package com.example.fruits.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@NoArgsConstructor
public class FruitsImageDetails {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer fidId;
    private Integer fId;
    private String image;

    public FruitsImageDetails(Integer fId) {
        this.fId = fId;
    }
}
