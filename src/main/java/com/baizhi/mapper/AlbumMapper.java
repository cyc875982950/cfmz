package com.baizhi.mapper;

import com.baizhi.entity.Album;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AlbumMapper {
    List<Album> findAll(@Param("start") Integer start,@Param("rows")Integer rows);
    void updateAll(Album album);
    void delete(String id);
    void add(Album album);
    void upload(Album album);
    Integer count();
    void updateCount(Album album);
}
