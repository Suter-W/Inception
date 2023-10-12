package com.inception.backend.service.serviceImpl;

import com.inception.backend.mapper.LoginMapper;
import com.inception.backend.pojo.User;
import com.inception.backend.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginMapper loginMapper;

    public User login(String userPhoneNum, String userPassword){
        return loginMapper.getByUserPhoneNumAndPassword(userPhoneNum,userPassword);
    }

    public void register(User user) {
        loginMapper.register(user);
    }


    public User phoneNumValid(String userPhoneNum) {
        return loginMapper.getByUserPhoneNum(userPhoneNum);
    }
}
