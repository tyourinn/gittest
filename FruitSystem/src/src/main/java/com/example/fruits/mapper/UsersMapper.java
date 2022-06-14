package com.example.fruits.mapper;

import com.example.fruits.domain.Users;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

public interface UsersMapper extends Mapper<Users> {

    @Select("select count(*)>0 from users where account=#{account}")
    boolean existAccount(@Param("account") String account);

}
