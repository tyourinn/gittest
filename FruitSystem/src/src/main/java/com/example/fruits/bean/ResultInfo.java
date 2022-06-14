package com.example.fruits.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResultInfo {

    private Boolean flag;
    private String message;
    private Object result;

    public ResultInfo(boolean flag, String message) {
        this.flag = flag;
        this.message = message;
    }

    public ResultInfo(Boolean flag, String message, Object result) {
        this.flag = flag;
        this.message = message;
        this.result = result;
    }
}
