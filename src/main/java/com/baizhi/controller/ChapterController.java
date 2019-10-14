package com.baizhi.controller;

import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;
import com.baizhi.service.AlbumService;
import com.baizhi.service.ChapterService;
import org.apache.commons.io.FileUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("chapter")

public class ChapterController {
    @Autowired
    ChapterService chapterService;

    @Autowired
    AlbumService albumService;
    @RequestMapping("findAll")
    public Map<String,Object> findAll(Integer page,Integer rows,String albumid){
        Map<String, Object> all = chapterService.findAll(page, rows,albumid);
        return all;
    }
    @RequestMapping("edit")
    public String edit(Chapter chapter,String [] id, String albumid, MultipartFile file_path, HttpSession session, String oper){
            if("add".equals(oper)){
                try {
                    String s = UUID.randomUUID().toString();
                    chapter.setId(s);
                    chapter.setAlbum_id(albumid);
                    updateCount(albumid);
                    chapterService.add(chapter,session);
                    return s;
                } catch (Exception e) {
                 e.printStackTrace();
                }
                return null;
            }else if ("del".equals(oper)){

                deleteCount(albumid,id.length);
                chapterService.delete(id);

                return null;
            }else {
                chapterService.updateAll(chapter);
                return null;
            }
    }
    @RequestMapping("upload")
    public void upload(HttpSession session, String albumId, MultipartFile file_path) throws IOException, ReadOnlyFileException, TagException, InvalidAudioFrameException, CannotReadException {
        //获取mp3路径
        String realPath = session.getServletContext().getRealPath("/mp3");

        File file = new File(realPath);
        if(!file.exists()){
            file.mkdirs();
        }

        String originalFilename = file_path.getOriginalFilename();
        String newFileName = UUID.randomUUID().toString()+"_"+originalFilename;
        try {
            file_path.transferTo(new File(file+"/"+newFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //修改
        Chapter chapter = new Chapter();
        chapter.setId(albumId);
        chapter.setFile_path(newFileName);
        contextLoads(session,chapter);

        chapterService.upload(chapter);
    }
    @RequestMapping("download")
    public void download(HttpSession session, String audioName,HttpServletResponse response) throws IOException {
        String realPath = session.getServletContext().getRealPath("/mp3");
        File file = new File(realPath,audioName);
        //拆分
        String s = audioName.split("_")[1];
        //编码防止乱码
        System.out.println(s);
        String encode = URLEncoder.encode(s, "UTF-8");
        //替换名字中的字符
        String replace = encode.replace("+", "%20");
        response.setHeader("content-disposition","attachment;filename="+replace);
        //写出去
        FileUtils.copyFile(file,response.getOutputStream());
    }
    //修改数量
    private void updateCount(String id){
        Integer count = chapterService.count(id);
        Integer c=count+1;
        String s = c.toString();
        Album album1 = new Album();
        album1.setCounts(s);
        album1.setId(id);
        albumService.updateCount(album1);
    }

    //减去数量
    private void deleteCount(String id,Integer mang){
        Integer count = chapterService.count(id);
        Integer c=count-mang;
        String s = c.toString();
        Album album1 = new Album();
        album1.setCounts(s);
        album1.setId(id);
        albumService.updateCount(album1);
    }
    public  void contextLoads(HttpSession session,Chapter chapter) throws TagException, ReadOnlyFileException, CannotReadException, InvalidAudioFrameException, IOException {
        String file_path = chapter.getFile_path();
        System.out.println(file_path);
        //获取文件位置
        String realPath = session.getServletContext().getRealPath("/mp3/"+file_path);
        File file=new File(realPath);
        //获取文件大小  单位是字节 byte
        long length = file.length();
        //获取音频时长 单位是秒      AudioFile类需要引入额外依赖 jaudiotagger
        AudioFile read = AudioFileIO.read(file);
        AudioHeader header = read.getAudioHeader();
        int trackLength = header.getTrackLength();
        //获取分钟数
        Integer m =trackLength/60;
        //获取秒秒数
        Integer s =trackLength%60;
        //将文件大小强转成double类型
        double size=(double)length;
        //获取文件大小 单位是MB
        double ll=size/1024/1024;
        //取double小数点后两位  四舍五入+MB
        BigDecimal bg = new BigDecimal(ll).setScale(2, RoundingMode.UP);
        chapter.setSize(bg+"MB");
        chapter.setTimes(m+"分"+s+"秒");
    }
}
