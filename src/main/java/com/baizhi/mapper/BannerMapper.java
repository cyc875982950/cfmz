package com.baizhi.mapper;

import com.baizhi.entity.Banner;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BannerMapper {
    List<Banner> findAll(@Param("start") Integer strat,@Param("rows") Integer rows);
    void add(Banner banner);
    void update(Banner banner);
    Integer count();
    void delete(String id);
    void updateAll(Banner banner);
    List<Banner> queryAll();
}
