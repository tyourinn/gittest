package com.example.fruits.exception;


import com.example.fruits.enums.ExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LyException extends RuntimeException {

    private ExceptionEnum exceptionEnum;

}
