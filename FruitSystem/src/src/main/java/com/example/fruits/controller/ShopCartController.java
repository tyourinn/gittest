package com.example.fruits.controller;

import com.example.fruits.domain.ShopCart;
import com.example.fruits.service.ShopCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("cart")
public class ShopCartController {

    @Autowired
    private ShopCartService shopCartService;

    /**
     * 加入购物车
     * @param fId
     * @return true标识新增，false标识未新增
     */
    @PostMapping("/{fId}")
    public ResponseEntity<Boolean> addItem(@PathVariable("fId") Integer fId){
        return ResponseEntity.status(HttpStatus.CREATED).body(shopCartService.addItem(fId));
    }

    //获取用户购物车信息
    @GetMapping
    public ResponseEntity<List<ShopCart>> cart(){
        return ResponseEntity.ok(shopCartService.userCart());
    }

    //删除购物车项
    @DeleteMapping
    public ResponseEntity<Void> deleteItem(@RequestBody Integer[] scIds){
        shopCartService.deleteItem(scIds);
        return ResponseEntity.ok().build();
    }

    //购物车条目购买数量增加
    @PutMapping("/add/{scId}")
    public ResponseEntity<Void> cartItemNumAdd(@PathVariable("scId") Integer scId){
        shopCartService.cartItemNumAdd(scId);
        return ResponseEntity.ok().build();
    }

    //购物车条目购买数量减少
    @PutMapping("/reduce/{scId}")
    public ResponseEntity<Void> cartItemNumReduce(@PathVariable("scId") Integer scId){
        shopCartService.cartItemNumReduce(scId);
        return ResponseEntity.ok().build();
    }


}
