package com.example.fruits.service;

import com.example.fruits.domain.Users;
import com.example.fruits.utils.SpringbootTestBase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class UsersServiceTest extends SpringbootTestBase {

    @Autowired
    private UsersService usersService;

    @Test
    public void register() {
        Users users = new Users();
        users.setAccount("yj123");
        users.setPassword("yj123");
        users.setUserName("杨俊");
        usersService.register(users);
    }
}