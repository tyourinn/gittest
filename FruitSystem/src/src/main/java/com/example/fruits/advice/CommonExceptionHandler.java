package com.example.fruits.advice;

import com.example.fruits.bean.ExceptionResult;
import com.example.fruits.exception.LyException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 统一异常处理
 */
@ControllerAdvice   //针对controller的通知，默认情况下会拦截所有的controller
public class CommonExceptionHandler {

    @ExceptionHandler(LyException.class)  //拦截的异常
    public ResponseEntity<ExceptionResult> handleException(LyException e){
        return ResponseEntity.status(e.getExceptionEnum().getCode())
                .body(new ExceptionResult(e.getExceptionEnum()));
    }

}
