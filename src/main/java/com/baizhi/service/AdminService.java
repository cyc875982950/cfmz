package com.baizhi.service;

import javax.servlet.http.HttpSession;
import java.util.Map;

public interface AdminService {
    public Map<String,Object> findOne(String username, String password, HttpSession session, String yzm);
}
