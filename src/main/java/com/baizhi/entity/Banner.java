package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Banner {
    @Excel(name = "编号")
    private String id;
    @Excel(name="标题")
    private String title;
    @Excel(name = "简介")
    private String dese;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发布时间",needMerge = true,width = 20,format = "yyyy-MM-dd")
    private Date create_time;
    @Excel(name = "图片",needMerge = true,type = 2,width = 20)
    private String img;
    @Excel(name = "状态")
    private String status;
}
