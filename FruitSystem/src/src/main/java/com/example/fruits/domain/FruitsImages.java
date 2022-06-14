package com.example.fruits.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@NoArgsConstructor
public class FruitsImages {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer fiId;
    private Integer fId;
    private String image;

    public FruitsImages(Integer fId) {
        this.fiId = fId;
    }
}
