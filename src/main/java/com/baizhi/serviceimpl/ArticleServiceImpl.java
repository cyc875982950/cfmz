package com.baizhi.serviceimpl;

import com.baizhi.entity.Article;
import com.baizhi.mapper.ArticleMapper;
import com.baizhi.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    ArticleMapper articleMapper;
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Map<String, Object> findAll(Integer page, Integer rows) {
        Map<String,Object> map =new HashMap<>();
        Integer count = articleMapper.count();
        Integer total = count%rows==0?count/rows:count/rows+1;
        Integer start =(page-1)*rows;
        List<Article> all = articleMapper.findAll(start, rows);
        map.put("rows",all);
        map.put("page",page);
        map.put("total",total);
        map.put("records",count);
        return map;
    }

    @Override
    public void add(Article article) {
    articleMapper.add(article);
    }

    @Override
    public void updateAll(Article article) {
    articleMapper.updateAll(article);
    }
}
