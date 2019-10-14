package com.baizhi.service;

import com.baizhi.entity.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> queryAll();
    List<Integer> selectByprovince(Integer day);
}
