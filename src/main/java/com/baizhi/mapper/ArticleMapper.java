package com.baizhi.mapper;

import com.baizhi.entity.Article;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleMapper {
    List<Article> findAll(@Param("starts") Integer starts,@Param("rows") Integer rows);
    Integer count();
    void add(Article article);
    void updateAll(Article article);
}
