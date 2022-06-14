package com.example.fruits.mapper;

import com.example.fruits.domain.Address;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface AddressMapper extends Mapper<Address> {
    @Select("select count(*)>0  from t_order join order_item using(o_id) where add_id = #{addId}")
    boolean exists(@Param("addId") Integer addId);

    @Update("UPDATE address SET is_show=1 WHERE add_id=#{addId} ")
    void deleteAddress(@Param("addId") Integer addId);

    @Select("select add_id,name,phone,addr,add_details,u_id,is_default from address WHERE u_id=#{uId} and is_show =0")
    List<Address> selectAddress(@Param("uId") Integer uId);
}
