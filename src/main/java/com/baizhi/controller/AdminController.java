package com.baizhi.controller;

import com.baizhi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("admin")
public class AdminController {
    @Autowired
    AdminService adminService;

    @RequestMapping("findOne")
    public Map<String,Object> findOne(String username, String password, String yzm, HttpSession session){
        Map<String, Object> map = adminService.findOne(username,password,session,yzm);
        return map;
    }
}
