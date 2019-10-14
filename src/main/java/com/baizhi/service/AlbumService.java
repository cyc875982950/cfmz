package com.baizhi.service;

import com.baizhi.entity.Album;

import java.util.Map;

public interface AlbumService {
    Map<String,Object> findAll(Integer page, Integer rows);
    void updateAll(Album album);
    void delete(String [] id);
    void add(Album album);
    void uploadImg(Album album);
    void updateCount(Album album);
}
