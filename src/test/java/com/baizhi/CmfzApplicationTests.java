package com.baizhi;

import com.baizhi.entity.Admin;
import com.baizhi.entity.Album;
import com.baizhi.entity.Banner;
import com.baizhi.entity.Chapter;
import com.baizhi.mapper.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CmfzApplicationTests {
    @Autowired
    AdminMapper adminMapper;
    @Autowired
    BannerMapper bannerMapper;
    @Autowired
    AlbumMapper albumMapper;
    @Autowired
    ChapterMapper chapterMapper;
    @Autowired
    UserMapper userMapper;

    @Test
    public void findOne() {
        Admin one = adminMapper.findOne("root", "root");
        if(one==null){
            System.out.println("登录失败");
        }else {
            System.out.println("登录成功");
        }
    }

    @Test
    public void findAll() {
        List<Banner> all = bannerMapper.findAll(0,3);
        for (Banner banner : all) {
            System.out.println(banner);
        }
    }

    @Test
    public void count() {
        Integer count = bannerMapper.count();
        System.out.println(count);
    }

    @Test
    public void add() {
        Banner banner=new Banner();
        banner.setId(UUID.randomUUID().toString());
        banner.setTitle("花里胡哨的");
        banner.setStatus("冻结");
        banner.setCreate_time(new Date());
        banner.setDese("好好");
        banner.setImg("A2.jpg");
        bannerMapper.add(banner);
    }

    @Test
    public void findA() {
        List<Album> all = albumMapper.findAll(0, 2);
        for (Album album : all) {
            System.out.println(album);
        }
    }

    @Test
    public void findAl() {
        List<Chapter> all = chapterMapper.findAll(0, 2,"1");
        for (Chapter chapter : all) {
            System.out.println(chapter);
        }
    }

    @Test
    public void insert() {
        Chapter chapter=new Chapter();
        chapter.setId("5");
        chapter.setAlbum_id("2");
        chapterMapper.add(chapter);
    }

    @Test
    public void insert1() {
        Integer integer = userMapper.selectByprovince(7);
        System.out.println(integer);
        System.out.println("-------------------------");
        System.out.println("吃饭");
    }
}
