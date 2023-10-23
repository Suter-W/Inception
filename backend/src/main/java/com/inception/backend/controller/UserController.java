package com.inception.backend.controller;

import com.inception.backend.pojo.Result;
import com.inception.backend.pojo.User;
import com.inception.backend.service.UserService;
import jakarta.annotation.security.PermitAll;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/User")
@PermitAll
@CrossOrigin
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public Result getUserById(@RequestParam Integer id){
        log.info("查找用户：{}",id);
        User u = userService.getById(id);
        return Result.success(u);
    }


}
