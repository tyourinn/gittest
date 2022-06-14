package com.example.fruits.service;

import com.example.fruits.domain.OrderItem;
import com.example.fruits.domain.Review;
import com.example.fruits.domain.Users;
import com.example.fruits.mapper.OrderItemMapper;
import com.example.fruits.mapper.ReviewMapper;
import com.example.fruits.utils.UserContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;

@Service
public class ReviewService {

    @Autowired
    private ReviewMapper reviewMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    //评论
    @Transactional
    public void reviewFruits(Review reviewVo) {
        //获取用户信息
        Users user = UserContextUtil.getUser();
        //设置用户uId
        reviewVo.setUId(user.getuId());
        //设置评论时间
        reviewVo.setRTime(new Timestamp(new Date().getTime()));
        //持久化数据
        reviewMapper.insert(reviewVo);
        //设置商品该订单已评论
        orderItemMapper.updateByPrimaryKeySelective(new OrderItem().setOiId(reviewVo.getOiId()).setReview(true));
    }
}
