package com.baizhi.controller;


import com.baizhi.entity.Article;
import com.baizhi.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("article")
public class ArticleContorller {
    @Autowired
    ArticleService articleService;

    @RequestMapping("queryAll")
    public Map<String, Object> queryAll(Integer page, Integer rows) {
        Map<String, Object> map = articleService.findAll(page, rows);
        return map;
    }
    @RequestMapping("add")
    public void add(Article article){
        article.setCreate_date(new Date());
        article.setId(UUID.randomUUID().toString());
        articleService.add(article);
    }
    @RequestMapping("update")
    public void update(Article article){
        articleService.updateAll(article);
    }
}
