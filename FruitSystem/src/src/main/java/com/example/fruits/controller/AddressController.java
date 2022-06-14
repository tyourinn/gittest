package com.example.fruits.controller;

import com.example.fruits.domain.Address;
import com.example.fruits.service.AddressService;
import com.example.fruits.vo.AddressVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("addr")
public class AddressController {

    @Autowired
    private AddressService addressService;

    //获取用户收货地址
    @GetMapping
    public ResponseEntity<List<AddressVo>> getAddress(){
        return ResponseEntity.ok(addressService.address());
    }

    //设置用户默认收货地址
    @PutMapping("/default/{addId}")
    public ResponseEntity<Void> setDefault(@PathVariable("addId") Integer addId){
        addressService.setDefault(addId);
        return ResponseEntity.ok().build();
    }

    //删除地址
    @DeleteMapping("/{addId}")
    public ResponseEntity<Void> deleteAddr(@PathVariable("addId") Integer addId){
        addressService.deleteAddr(addId);
        return ResponseEntity.ok().build();
    }

    //添加收货地址
    @PostMapping
    public ResponseEntity<Void> saveAddr(@RequestBody Address addressV){
        addressService.saveAddr(addressV);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //修改地址信息
    @PutMapping
    public ResponseEntity<Void> modifyAddr(@RequestBody Address addressV){
        addressService.modifyAddr(addressV);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/phone/{addId}")
    public ResponseEntity<String > addPhone(@PathVariable("addId") Integer addId){
        return ResponseEntity.ok(addressService.addPhone(addId));
    }
}
