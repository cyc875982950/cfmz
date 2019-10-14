package com.baizhi.serviceimpl;

import com.baizhi.entity.Chapter;
import com.baizhi.mapper.ChapterMapper;
import com.baizhi.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
@Transactional
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    ChapterMapper chapterMapper;
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Map<String, Object> findAll(Integer page, Integer rows,String albumid) {
        //总条数
        Integer count = chapterMapper.count(albumid);
        Integer total =  count%rows==0?count/rows:count/rows+1;
        Integer start = (page-1)*rows;
        List<Chapter> all = chapterMapper.findAll(start, rows,albumid);
        Map<String,Object> map =new HashMap<>();
        map.put("total",total);
        map.put("records",count);
        map.put("page",page);
        map.put("rows",all);
        return map;
    }
    @Override
    public void add(Chapter chapter,HttpSession session){
        chapterMapper.add(chapter);
    }

    @Override
    public void delete(String[] id) {
        for (String s : id) {
            chapterMapper.delete(s);
        }
    }

    @Override
    public void upload(Chapter chapter) {
        chapterMapper.upload(chapter);
    }

    @Override
    public void updateAll(Chapter chapter) {
        chapterMapper.updateAll(chapter);
    }

    @Override
    public Integer count(String album_id) {

        return chapterMapper.count(album_id);
    }

}
