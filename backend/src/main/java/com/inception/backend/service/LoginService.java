package com.inception.backend.service;

import com.inception.backend.pojo.User;

public interface LoginService {
    /**
     * 员工登录校验
     * @param userPhoneNum
     * @param userPassword
     * @return
     */
    User login(String userPhoneNum, String userPassword);

    void register(User user);

    User phoneNumValid(String userPhoneNum);
}
