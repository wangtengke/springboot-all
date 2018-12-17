package com.springboot.jpa.controller;

import com.springboot.jpa.entity.User;
import com.springboot.jpa.service.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @program: springboot-all
 * @description:
 * @author: wangtengke
 * @create: 2018-12-08
 **/
@RestController
public class JpaController {
    @Resource
    UserRepository userRepository;
    @GetMapping("/findByName")
    public User findUserByName(String name){
        User condition = new User();
        condition.setName(name);
        List<User> list =userRepository.findAll();
        System.out.println(list.toString());
        return userRepository.findOne(Example.of(condition)).orElse(new User());
    }

    @PostMapping("/save")
    public void saveUser(@RequestBody User user){
        userRepository.save(user);
    }
}
