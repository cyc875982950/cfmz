package com.baizhi.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors
public class Article {
    private String id;
    private String title;
    private String content;
    private Date create_date;
    private String author;
    private String status;
}
