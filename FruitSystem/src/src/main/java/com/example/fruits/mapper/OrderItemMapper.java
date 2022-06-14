package com.example.fruits.mapper;

import com.example.fruits.domain.OrderItem;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface OrderItemMapper extends Mapper<OrderItem> {
    @Select("select * from t_order join order_item using(o_Id) where u_id = #{uId} and is_deliver=#{isDeliver} and is_play = #{isPay} and is_review = #{isReview} and is_take = #{isTake}")
    List<OrderItem> orderWaitDeliverByUser(@Param("uId") Integer uId, @Param("isDeliver") boolean isDeliver, @Param("isPay") boolean isPay, @Param("isReview")Boolean isReview, @Param("isTake")Boolean isTake);

    @Select("select * from t_order join order_item using(o_Id) where is_deliver=#{isDeliver} and is_play = #{isPay} and is_review = #{isReview} and is_take = #{isTake}")
    List<OrderItem> orderWaitDeliverByUserAll(@Param("isDeliver") boolean isDeliver, @Param("isPay") boolean isPay, @Param("isReview")Boolean isReview, @Param("isTake")Boolean isTake);
}
