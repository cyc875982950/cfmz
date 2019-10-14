package com.baizhi.serviceimpl;

import com.baizhi.entity.Admin;
import com.baizhi.mapper.AdminMapper;
import com.baizhi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminMapper adminMapper;
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Map<String,Object> findOne(String username, String password, HttpSession session,String yzm) {
        Admin one = adminMapper.findOne(username, password);
        Map<String,Object> map =new HashMap<>();
        Object validationCode = session.getAttribute("validationCode");
        if(validationCode.equals(yzm)) {
            if (one != null) {
                map.put("one",one);
              session.setAttribute("name",one.getName());
                return map;
            } else {
                Object error = "登录失败,请检查您的账号或密码。";
                map.put("error",error);
                return map;
            }
        }else {
            Object vali="验证码错误";
                map.put("vali",vali);
            return map;
        }
    }
}
