package com.example.fruits.controller;

import com.example.fruits.domain.Review;
import com.example.fruits.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    //评论
    @PostMapping
    public ResponseEntity<Void> reviewFruits(@RequestBody Review reviewVo){
        reviewService.reviewFruits(reviewVo);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
