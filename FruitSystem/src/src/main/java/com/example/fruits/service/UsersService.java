package com.example.fruits.service;

import com.example.fruits.domain.Users;
import com.example.fruits.enums.ExceptionEnum;
import com.example.fruits.exception.LyException;
import com.example.fruits.mapper.UsersMapper;
import com.example.fruits.utils.UserContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class UsersService {

    @Autowired
    private UsersMapper usersMapper;

    //注册
    public void register(Users users) {
        //非空验证
        if(StringUtils.isBlank(users.getUserName())){   //用户名为空
            throw new LyException(ExceptionEnum.USER_NAME_NOT_ALLOW_NULL);
        }
        if(StringUtils.isBlank(users.getAccount())){   //账号为空
            throw new LyException(ExceptionEnum.USER_ACCOUNT_NOT_ALLOW_NULL);
        }
        if(StringUtils.isBlank(users.getPassword())){   //密码为空
            throw new LyException(ExceptionEnum.USER_PASSWORD_NOT_ALLOW_NULL);
        }
        if(usersMapper.existAccount(users.getAccount())){//账号已存在
            throw new LyException(ExceptionEnum.ACCOUNT_EXIST);
        }
        //默认头像（无头像图片）
        users.setHeadImage("/images/not_login.png");
        //2021/10/19 fan add start
        //更新日時
        Date dateObj = new Date();
        SimpleDateFormat format = new SimpleDateFormat( "yyyy/MM/dd HH:mm:ss" );
        String currentTime = format.format( dateObj );
        users.setUpdateTime(currentTime);
        //パスワード加密对应 change start
        String psMd5 = users.getPassword();
        String jiami = DigestUtils.md5DigestAsHex(psMd5.getBytes());
        users.setPassword(jiami);
        //パスワード加密对应 change end

        //2021/10/19 fan add end
        //持久化用户信息
        int insert = usersMapper.insert(users);
        if(insert<0){   //注册失败
            log.info(users.getUserName()+" 注册失败！--> UsersService");
            throw new LyException(ExceptionEnum.REGISTER_FAILED);
        }
    }

    //登录
    public void login(Users users) {
        if(StringUtils.isBlank(users.getAccount())){   //账号为空
            throw new LyException(ExceptionEnum.USER_ACCOUNT_NOT_ALLOW_NULL);
        }
        if(StringUtils.isBlank(users.getPassword())){   //密码为空
            throw new LyException(ExceptionEnum.USER_PASSWORD_NOT_ALLOW_NULL);
        }
        //根据账号到用户表查询用户信息
        List<Users> usersByAccount = usersMapper.select(new Users(users.getAccount()));
        if(CollectionUtils.isEmpty(usersByAccount)){   //账号不存在
            throw new LyException(ExceptionEnum.ACCOUNT_NOT_EXIST);
        }
        //パスワード加密对应 change start
        String psMd5 = users.getPassword();
        String jiamiDB = DigestUtils.md5DigestAsHex(psMd5.getBytes());
        //加密密码经行匹配验证
        for (Users usersDB : usersByAccount) {
            if(usersDB.getPassword().equals(jiamiDB)){  //匹配成功
                //パスワード加密对应 change end
                //设置session
                UserContextUtil.setUser(usersDB);
                //退出
                return;
            }
        }
//        for (Users usersDB : usersByAccount) {
//            if(usersDB.getPassword().equals(getPassword()){  //匹配成功
//                //设置session
//                UserContextUtil.setUser(usersDB);
//                //退出
//                return;
//            }
//        }
        //密码错误
        throw new LyException(ExceptionEnum.PASSWORD_ERROR);
    }
}
