package com.baizhi.serviceimpl;

import com.baizhi.entity.dto.UserDto;
import com.baizhi.mapper.UserMapper;
import com.baizhi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<UserDto> queryAll() {
        return userMapper.queryAll();
    }

    @Override
    public List<Integer> selectByprovince(Integer day) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            Integer integer = userMapper.selectByprovince(i);
            list.add(integer);
        }
        return list;
    }
}
