package com.example.fruits.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@NoArgsConstructor
public class Users {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer uId;
    private String userName;
    private String account;
    private String password;
    private String headImage;
    //2021/10/19 fan add start
    //更新日時
    private String updateTime;
    //2021/10/19 fan add end

    public Users(String account) {
        this.account = account;
    }

    @JsonIgnore
    public String getPassword() {

        return password;
    }

    @JsonIgnore
    public String getAccount() {
        return account;
    }

    @JsonIgnore
    public Integer getuId() {
        return uId;
    }
}
