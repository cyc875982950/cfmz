package com.baizhi.service;

import com.baizhi.entity.Banner;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface BannerService {
    Map<String,Object> findAll(Integer page, Integer rows);
    void add(Banner banner);
    void update(Banner banner);
    void updateAll(Banner banner);
    void delete(String id);
    List<Banner> queryAll(HttpServletResponse response) throws IOException;
}
