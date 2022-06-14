package com.example.fruits.controller;

import com.example.fruits.bean.Page;
import com.example.fruits.domain.Fruits;
import com.example.fruits.service.FruitsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("fruits")
@RestController
public class FruitsController {

    @Autowired
    private FruitsService fruitsService;

    //首页分页获取水果信息，并可搜索
    @GetMapping
    public ResponseEntity<Page<Fruits>> index(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                      @RequestParam(value = "size",defaultValue = "9") Integer size,
                                      @RequestParam(value = "search",required = false) String search){
        return ResponseEntity.ok(fruitsService.index(page,size,search));
    }

    //水果详细
    @GetMapping("/detail")
    public ResponseEntity<Fruits> detail(@RequestParam("fId") Integer fId){
        return ResponseEntity.ok(fruitsService.detail(fId));
    }

}
