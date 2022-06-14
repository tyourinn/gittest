package com.example.fruits.controller;

import com.example.fruits.bean.ResultInfo;
import com.example.fruits.service.OrderService;
import com.example.fruits.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("order")
public class OrderController {
    
    @Autowired
    private OrderService orderService;

    /**
     *   商品详情页，立即支付
     *   根据商品fId，获取订单详情数据
     * @param fId
     * @return
     */
    @GetMapping("/orderDetailQuick/{fId}")
    public ResponseEntity<OrderDetailQuickVo> orderDetailQuick(@PathVariable("fId") Integer fId){
        return ResponseEntity.ok( orderService.orderDetailByFId(fId));
    }


    /**
     *    购物车页，去结算
     *    根据购物车scId，获取订单详情数据
     * @param scIds
     * @return
     */
    @PostMapping(value = "/orderDetail")
    public ResponseEntity<OrderDetailQuickVo> orderDetail(@RequestBody Integer[] scIds){
        return ResponseEntity.ok( orderService.orderDetailByScId(scIds));
    }

    /**
     * 提交订单
     * @param addId
     * @param fruitsVos
     * @param response
     * @return 返回生成的订单id
     */
    @PostMapping("/{addId}")
    public ResponseEntity<Integer>  addOrder(@PathVariable("addId") Integer addId,
                                         @RequestBody  List<FruitsVo> fruitsVos,
                                         HttpServletResponse response){
        return ResponseEntity.ok( orderService.addOrder(addId,fruitsVos,response));
    }

    //根据订单oId 查询待付款信息
    @GetMapping("/wait/{oId}")
    public ResponseEntity<OrderWaitPayVo> orderWaitPay(@PathVariable("oId") Integer oId ){
        return ResponseEntity.ok(orderService.orderWaitPay(oId));
    }

     //确认支付
    @PostMapping("/pay/{oId}")
    public ResponseEntity<ResultInfo> payOrder(@PathVariable("oId") Integer oId){
        return ResponseEntity.ok(orderService.payOrder(oId));
    }

    //查询订单 各状态的数量信息
    @GetMapping("/userCenter")
    public ResponseEntity<MyCenterVo>  UserCenter(){
        return ResponseEntity.ok(orderService.UserCenter());
    }

    //获取用户待付款订单信息
    @GetMapping("/waitPay")
    public ResponseEntity<OrderStatusItem> orderWaitPayByUser(){
        return ResponseEntity.ok(orderService.orderWaitPayByUser());
    }

    //获取用户待发货订单信息
    @GetMapping("/waitDeliver")
    public ResponseEntity<OrderDetailItem> orderWaitDeliverByUser(){
        return ResponseEntity.ok(orderService.orderWaitDeliverByUser());
    }

    //获取用户待收货订单信息
    @GetMapping("/waitTake")
    public ResponseEntity<OrderDetailItem> orderWaitTakeByUser(){
        return ResponseEntity.ok(orderService.orderWaitTakeByUser());
    }

    //获取用户待评价订单信息
    @GetMapping("/waitReview")
    public ResponseEntity<OrderDetailItem> orderWaitReviewByUser(){
        return ResponseEntity.ok(orderService.orderWaitReviewByUser());
    }

    //取消订单
    @DeleteMapping("/{oId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable("oId") Integer oId){
        orderService.deleteOrder(oId);
        return ResponseEntity.ok().build();
    }

    //确认收货
    @PutMapping("/{oiId}")
    public ResponseEntity<Void> confirmTake(@PathVariable("oiId") Integer oiId){
        orderService.confirmTake(oiId);
        return ResponseEntity.ok().build();
    }

    //获取用户各订单状态
    @GetMapping
    public ResponseEntity<OrderStatusNum> oderStatusNum(){
        return ResponseEntity.ok(orderService.oderStatusNum());
    }

}
