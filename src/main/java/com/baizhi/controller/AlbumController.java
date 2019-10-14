package com.baizhi.controller;

import com.baizhi.entity.Album;
import com.baizhi.service.AlbumService;
import com.baizhi.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("album")
public class AlbumController {
    @Autowired
    AlbumService albumService;
    @Autowired
    ChapterService chapterService;
    @RequestMapping("findAll")
    public Map<String,Object> findAll(Integer page,Integer rows){
        Map<String, Object> map = albumService.findAll(page, rows);
        return map;
    }
    @RequestMapping("edit")
    public String edit(Album album,String [] id,String oper){
        if("add".equals(oper)){
            String s = UUID.randomUUID().toString();
            album.setId(s);
            Integer count = chapterService.count(s);
            System.out.println(count);
            String counts =count.toString();
            album.setCounts(counts);
            albumService.add(album);
            return s;
        }else if("del".equals(oper)){
            albumService.delete(id);
            return null;
        }else {
            albumService.updateAll(album);
            System.out.println("修改");
            return null;
        }
    }
    @RequestMapping("upload")
    public void upload(HttpSession session, String albumId, MultipartFile cover){
        //获取当前文件夹的路径
        String realPath = session.getServletContext().getRealPath("img");
        File file = new File(realPath);
        if(!file.exists()){
            file.mkdirs();
        }
        //获取图片的名字
        String originalFilename = cover.getOriginalFilename();
        String newFileName =UUID.randomUUID().toString()+"-"+originalFilename;
        try {
            cover.transferTo(new File(file+"/"+newFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Album album = new Album();
        album.setId(albumId);
        album.setCover(newFileName);
        albumService.uploadImg(album);
    }
}
