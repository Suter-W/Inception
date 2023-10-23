package com.inception.backend.controller;


import com.inception.backend.pojo.Result;
import com.inception.backend.pojo.User;
import com.inception.backend.service.LoginService;
import jakarta.annotation.security.PermitAll;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Login")
@PermitAll
@CrossOrigin
@Slf4j
public class LoginController {
    @Autowired
    private LoginService loginService;

    public Boolean phoneNumValid(String userPhoneNum){
        User u = loginService.phoneNumValid(userPhoneNum);
        return u != null ? false : true ;
    }

    /**
     * 用户登录校验
     * @param user
     * @return Result
     */
    @PostMapping("/login")
    public Result login(@RequestBody User user){
        log.info("用户登录：{}",user.getUserPhoneNum());
        User u = loginService.login(user.getUserPhoneNum(), user.getUserPassword());
        return u != null?Result.success(u.getUserID()):Result.error("用户名或密码错误");
    }

    @PostMapping("/register")
    public Result register(@RequestBody User user){
        if(phoneNumValid(user.getUserPhoneNum())){
            log.info("用户注册：{}",user.getUserPhoneNum());
            loginService.register(user);
            return Result.success();
        }
        else{
            return Result.error("该手机号已被注册");
        }
   }
}
