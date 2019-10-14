package com.baizhi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.*;


@RestController
@RequestMapping("kindeditor")
public class KindeditorController {
    @RequestMapping("upload")
    public Map<String,Object> upload(MultipartFile img, HttpServletRequest request) throws IOException {
        Map<String,Object> map=new HashMap<>();
        String realPath = request.getSession().getServletContext().getRealPath("/picture");
        File file = new File(realPath);
        if(!file.exists()){
            file.mkdirs();
        }

        String newFileName =new Date().getTime()+"_"+img.getOriginalFilename();
        System.out.println(newFileName);
        img.transferTo(new File(realPath,newFileName));
        //获取当前网站的协议名
        String scheme = request.getScheme();
        //获取当前本机ip地址
        InetAddress localHost = InetAddress.getLocalHost();
        String hostAddress = localHost.getHostAddress();
        //获取当前端口号8989
        int serverPort = request.getServerPort();
        //获取当前项目名
        String contextPath = request.getContextPath();
        String url=scheme+"://"+hostAddress+":"+serverPort+"/picture/"+newFileName;
        //"http://localhost:8989/cmfz/img/"
        try{
            map.put("error",0);
            map.put("url",url);
        }catch (Exception e){
            map.put("error",1);
            map.put("url",url);
        }
        return map;
    }
    @RequestMapping("allImages")
    public Map<String,Object> all(HttpServletRequest request) throws UnknownHostException {
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> list = new ArrayList<>();
        String realPath = request.getSession().getServletContext().getRealPath("/picture");
        File file = new File(realPath);
        String[] allimg = file.list();
        for (String s : allimg) {
            Map<String, Object> map1 = new HashMap<>();
            map1.put("is_dir",false);
            map1.put("has_file",false);
            //获取文件的大小
            File file1 = new File(realPath,s);
            long length = file1.length();
            map1.put("filesize",length);
            map1.put("dir_path","");
            map1.put("is_photo",true);
            //获取文件的后缀名字
            String s1 = s.substring(s.lastIndexOf(".") + 1);
            map1.put("filetype",s1);
            map1.put("filename",s);
            if(s.contains("_")){
                String s2 = s.split("_")[0];
                Long aLong = Long.valueOf(s2);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String format1 = format.format(aLong);
                map1.put("datetime",format1);
            }else {
                map1.put("datetime",new Date());
            }
            list.add(map1);
        }

        map.put("moveup_dir_path","");
        map.put("current_dir_path","");
        //获取当前网站的协议名  http
        String scheme = request.getScheme();
        //获取当前本机ip地址
        InetAddress localHost = InetAddress.getLocalHost();
        String hostAddress = localHost.getHostAddress();
        //获取当前端口号
        int port = request.getServerPort();
        //获取项目名
        String path = request.getContextPath();
        String url=scheme+"://"+hostAddress+":"+port+path+"/picture/";
        map.put("current_url",url);
        int size = list.size();
        map.put("total_count",size);
        map.put("file_list",list);


               /*
        * {
            "moveup_dir_path": "",
            "current_dir_path": "",
            "current_url": "\/ke4\/php\/..\/attached\/",
            "total_count": 1,
            "file_list": [{
                "is_dir": false,
                "has_file": false,
                "filesize": 208736,
                "dir_path": "",
                "is_photo": true,
                "filetype": "jpg",
                "filename": "1241601537255682809.jpg",
                "datetime": "2018-06-06 00:36:39"
            },]
        }
        * */



        return map;
    }
}
