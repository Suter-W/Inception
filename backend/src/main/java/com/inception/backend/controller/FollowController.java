package com.inception.backend.controller;

import com.inception.backend.pojo.Follow;
import com.inception.backend.pojo.Host;
import com.inception.backend.pojo.Result;
import com.inception.backend.pojo.User;
import com.inception.backend.service.FollowService;
import jakarta.annotation.security.PermitAll;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Follow")
@PermitAll
@CrossOrigin
@Slf4j
public class FollowController {

    @Autowired
    private FollowService followService;

    @GetMapping("isFollow")
    public Result initHost(@RequestParam Integer userID,@RequestParam Integer hostID){
        log.info("初始化他人主页");
        Boolean isFollow = followService.isFollow(userID,hostID);
        User hostInfo = followService.getHostInfo(hostID);
        Host host = new Host();
        host.setHost(hostInfo);
        host.setIsFollow(isFollow);
        return Result.success(host);
    }

    @PostMapping("/follow")
    public Result createFollow(@RequestParam Integer userID,@RequestParam Integer hostID){
        log.info("创建关注关系");
        followService.createFollow(userID,hostID);
        return Result.success();
    }

    @PostMapping("/cancelFollow")
    public Result cancelFollow(@RequestParam Integer userID,@RequestParam Integer hostID){
        log.info("取消关注");
        followService.cancelFollow(userID,hostID);
        return Result.success();
    }

    @PostMapping("/getFollowList")
    public Result getFollowList(@RequestParam Integer userID){
        List<Follow> followList = followService.getFollowList(userID);
        return Result.success(followList);
    }

    @PostMapping("/getFanList")
    public Result getFanList(@RequestParam Integer userID){
        List<Follow> fanList = followService.getFanList(userID);
        return Result.success(fanList);
    }
}
