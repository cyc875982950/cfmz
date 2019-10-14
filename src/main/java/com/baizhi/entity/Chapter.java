package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors
public class Chapter {
    private String id;
    private String title;
    private String size;
    private String times;
    private String file_path;
    private String status;
    private String album_id;
}
