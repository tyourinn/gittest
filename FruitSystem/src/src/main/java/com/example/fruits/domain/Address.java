package com.example.fruits.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer addId;
    private String name;
    private String phone;
    private String addr;
    private String addDetails;
    private Integer uId;
    private boolean isShow;
    private Boolean isDefault;

    public Address(Integer uId, boolean isDefault) {
        this.uId = uId;
        this.isDefault = isDefault;
    }


    public Address(Integer uId) {
        this.uId = uId;
    }

    public Address(Integer uId, Integer addId) {
        this.uId = uId;
        this.addId = addId;
    }

    @JsonIgnore
    public Integer getuId() {
        return uId;
    }
}
