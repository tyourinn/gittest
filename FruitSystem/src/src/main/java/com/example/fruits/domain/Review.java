package com.example.fruits.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer rId;
    private Integer uId;
    private Integer fId;
    private String content;
    private Boolean isAnonymous;
    private Timestamp rTime;

    @Transient
    private Integer oiId;

    @Transient
    private  Users users;

    public Review(Integer fId) {
        this.fId = fId;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    public Timestamp getrTime() {
        return rTime;
    }
}
