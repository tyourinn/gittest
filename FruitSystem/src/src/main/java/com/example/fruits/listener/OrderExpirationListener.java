package com.example.fruits.listener;

import com.example.fruits.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;
import java.util.Date;

/**
 * @author mashu
 * Date 2020/5/17 23:01
 */
@Component
public class OrderExpirationListener extends KeyExpirationEventMessageListener {

    public OrderExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Autowired
    private OrderService orderService;

    @Value("${order.orderExpirePre}")
    private String orderExpirePre;    //订单过期时间 key 前缀

    @Override
    public void onMessage(Message message, byte[] pattern) {
        final String expiredKey = message.toString();
        System.out.println("我过期了" + expiredKey+"当前时间:"+new Date());
        //获取 订单前缀 的位置
        int preIndex = expiredKey.indexOf(orderExpirePre);
        //是 该系统存入的前缀
        if(preIndex>=0){
            String oId = expiredKey.substring(expiredKey.lastIndexOf("/")+1);
            orderService.orderExpire(Integer.parseInt(oId));
        }

    }
}