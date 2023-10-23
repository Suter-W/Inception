package com.inception.backend.service.serviceImpl;

import com.inception.backend.mapper.UserMapper;
import com.inception.backend.pojo.User;
import com.inception.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    public User getById(Integer id){
        return userMapper.getById(id);
    }
}
