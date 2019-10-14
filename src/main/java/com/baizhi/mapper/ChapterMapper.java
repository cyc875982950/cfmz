package com.baizhi.mapper;

import com.baizhi.entity.Chapter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChapterMapper {
    List<Chapter> findAll(@Param("start")Integer start,@Param("rows")Integer rows,@Param("album_id")String album_id);
    void add(Chapter chapter);
    void delete(String id);
    void updateAll(Chapter chapter);
    void upload(Chapter chapter);
    Integer count(String album_id);

}
