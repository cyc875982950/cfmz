package com.baizhi.mapper;

import com.baizhi.entity.dto.UserDto;

import java.util.List;

public interface UserMapper {
    List<UserDto> queryAll();
    Integer selectByprovince(Integer day);
}
