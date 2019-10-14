package com.baizhi.controller;

import com.baizhi.entity.dto.UserDto;
import com.baizhi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("upd")
public class UserController {
    @Autowired
    UserService userService;
    @RequestMapping("queryAll")
    public List<UserDto> queryAll(){
        List<UserDto> userDtos = userService.queryAll();
        return  userDtos;
    }
    @RequestMapping("count")
    public List<Integer> count(Integer day){
        List<Integer> list = userService.selectByprovince(day);
        System.out.println(list);
        return  list;
    }


}
