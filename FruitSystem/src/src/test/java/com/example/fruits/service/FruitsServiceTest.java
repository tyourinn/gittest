package com.example.fruits.service;

import com.example.fruits.bean.Page;
import com.example.fruits.domain.Fruits;
import com.example.fruits.utils.SpringbootTestBase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class FruitsServiceTest extends SpringbootTestBase {

    @Autowired
    private FruitsService fruitsService;

    @Test
    public void index() {
        Page<Fruits> fruitsPage = fruitsService.index(1, 9, "");
        System.out.println(fruitsPage);
    }
}