package com.example.fruits.controller;

import com.example.fruits.service.AdminService;
import com.example.fruits.vo.OrderDetailItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    //获取命令
    @GetMapping
    public ResponseEntity<Map<String,String>> cmd(){
        HashMap<String, String> map = new HashMap<>();
        map.put("/admin/order","查询所有用户的待发货信息");
        map.put("/admin/order/setDeliver/{oiId}","根据订单oiId设置商品发货");
        return ResponseEntity.ok(map);
    }

    //查询待发货信息
    @GetMapping("/order")
    public ResponseEntity<OrderDetailItem> waitDeliver(){
        return ResponseEntity.ok(adminService.waitDeliver());
    }

    //设置发货
    @GetMapping("/order/setDeliver/{oiId}")
    public ResponseEntity<Integer> setDeliver(@PathVariable("oiId") Integer oiId){
        return ResponseEntity.ok(adminService.setDeliver(oiId));
    }
}
