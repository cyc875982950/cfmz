package com.baizhi.controller;

import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@RestController
@RequestMapping("banner")
public class BannerController {
    @Autowired
    BannerService bannerService;
    @RequestMapping("findAll")
    public Map<String,Object> findAll(Integer page, Integer rows){
        Map<String, Object> map = bannerService.findAll(page, rows);
        return map;
    }

    @RequestMapping("edit")
    public String edit(Banner banner,String[]id,String oper){
        System.out.println(banner);
        if(oper.equals("add")) {
            String s = UUID.randomUUID().toString();
            banner.setId(s);
            bannerService.add(banner);
            return s;
        }else if(oper.equals("del")){
            for (String s : id) {
                bannerService.delete(s);
            }
            return null;
        }else {
            bannerService.updateAll(banner);
            return null;
        }
    }
    @RequestMapping("/upload")
    public void upload(MultipartFile img, String bannerId, HttpSession session){
        //获取img路径
        String realPath = session.getServletContext().getRealPath("/img");

        File file = new File(realPath);
        if(!file.exists()){
            file.mkdirs();
        }

        String originalFilename = img.getOriginalFilename();
        String newFileName = UUID.randomUUID().toString()+"_"+originalFilename;
        try {
            img.transferTo(new File(file+"/"+newFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //修改
        Banner banner =new Banner();
        banner.setId(bannerId);
        banner.setImg(newFileName);
        bannerService.update(banner);
    }
    @RequestMapping("/importOut")
    public void importOut(HttpServletResponse response) throws IOException {

        List<Banner> banners = bannerService.queryAll(response);
        System.out.println("ok------------------------");
    }
}
