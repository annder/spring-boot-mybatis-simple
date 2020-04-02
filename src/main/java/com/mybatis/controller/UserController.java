package com.mybatis.controller;

import com.mybatis.domain.User;
import com.mybatis.mapper.UserMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
public class UserController {
    @Autowired
    private UserMapper userMapper;
    @RequestMapping("/")
    public Integer getUser(){
        userMapper.insert("annter","root","12367800");
        return 1;
    }
    @PostMapping("/user")
    public String postUser (@Param("user") String user){
        return user;
    }
    @GetMapping("/getUserAll")
    public List<User> getUserAll (){
        List<User> findAllUser = userMapper.findAllUser();
        return findAllUser;
    }
}
