package com.baizhi.serviceimpl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baizhi.entity.Banner;
import com.baizhi.mapper.BannerMapper;
import com.baizhi.service.BannerService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class BannerServiceImpl implements BannerService {

    @Autowired
    BannerMapper bannerMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    //rows 是每页展示多少条数据
    public Map<String,Object> findAll(Integer page, Integer rows) {
        //总条数
        Integer count = bannerMapper.count();
        //计算页数
        Integer total = count%rows==0 ?count/rows:count/rows+1;
        //获取数据库中的起始条
        Integer begin=(page-1)*rows;
        List<Banner> all = bannerMapper.findAll(begin,rows);
        Map<String,Object> map=new HashMap<>();
        //rows 是轮播图列表
        map.put("rows",all);
        map.put("page",page);
        map.put("records",count);
        map.put("total",total);
        return map;
    }

    @Override
    public void add(Banner banner) {
        bannerMapper.add(banner);
    }

    @Override
    public void update(Banner banner) {
        bannerMapper.update(banner);
    }

    @Override
    public void updateAll(Banner banner) {
        bannerMapper.updateAll(banner);
    }

    @Override
    public void delete(String id) {
        bannerMapper.delete(id);
    }

    @Override
    public List<Banner> queryAll(HttpServletResponse response) throws IOException {
        List<Banner> banners = bannerMapper.queryAll();
        for (Banner banner : banners) {
            banner.setImg("E:\\sources\\cfmz\\cmfz\\src\\main\\webapp\\img\\"+banner.getImg());
        }
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("轮播图详情","表1"),
                Banner.class,banners);
        String encode = URLEncoder.encode("轮播图信息.xls","UTF-8");
        response.setHeader("content-disposition","attachment;filename="+encode);

        workbook.write(response.getOutputStream());
        return null;
    }
}
