package com.baizhi.service;

import com.baizhi.entity.Article;

import java.util.Map;

public interface ArticleService {
    Map<String,Object> findAll(Integer page, Integer rows);
    void add(Article article);
    void updateAll(Article article);
}
