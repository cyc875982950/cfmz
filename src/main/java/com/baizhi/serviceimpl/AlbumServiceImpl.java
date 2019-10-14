package com.baizhi.serviceimpl;

import com.baizhi.entity.Album;
import com.baizhi.mapper.AlbumMapper;
import com.baizhi.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    AlbumMapper albumMapper;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String,Object> findAll(Integer page, Integer rows) {
        Map<String,Object> map =new HashMap<>();
        Integer count = albumMapper.count();
        Integer total = count%rows==0?count/rows:count/rows+1;
        Integer start =(page-1)*rows;
        List<Album> all = albumMapper.findAll(start, rows);
        map.put("rows",all);
        map.put("page",page);
        map.put("total",total);
        map.put("records",count);
        return map;
    }

    @Override
    public void updateAll(Album album) {
        albumMapper.updateAll(album);
    }

    @Override
    public void delete(String[] id) {
        for (String s : id) {
            albumMapper.delete(s);
        }
    }

    @Override
    public void add(Album album) {
        album.setPublish_date(new Date());
        albumMapper.add(album);
    }

    @Override
    public void uploadImg(Album album) {
     albumMapper.upload(album);
    }

    @Override
    public void updateCount(Album album) {
        albumMapper.updateCount(album);
    }
}
