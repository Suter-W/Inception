package com.inception.backend.service.serviceImpl;

import com.inception.backend.mapper.FollowMapper;
import com.inception.backend.mapper.UserMapper;
import com.inception.backend.pojo.Follow;
import com.inception.backend.pojo.Result;
import com.inception.backend.pojo.User;
import com.inception.backend.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class FollowServiceImpl implements FollowService {
    @Autowired

    private FollowMapper followMapper;

    @Autowired

    private UserMapper userMapper;

    @Override
    public Boolean isFollow(Integer userID, Integer hostID) {
        List<Follow> follows = followMapper.isFollow(userID,hostID);
        return !follows.isEmpty();
    }

    @Override
    public void createFollow(Integer userID, Integer hostID) {
        LocalDateTime currentDateTime = LocalDateTime.now();

        // 定义日期时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // 格式化日期时间
        String formattedDateTime = currentDateTime.format(formatter);

        followMapper.Follow(userID,hostID,formattedDateTime);
    }

    @Override
    public void cancelFollow(Integer userID, Integer hostID) {
        followMapper.cancelFollow(userID,hostID);
    }

    @Override
    public User getHostInfo(Integer hostID) {
        return followMapper.getHostInfo(hostID);
    }

    @Override
    public List<Follow> getFollowList(Integer userID) {
        List<Follow> follows = followMapper.getFollowList(userID);
        for(Follow follow : follows){
            follow.setFolloweeInfo(userMapper.getById(follow.getFolloweeID()));
        }
        return follows;
    }

    @Override
    public List<Follow> getFanList(Integer userID) {
        List<Follow> follows = followMapper.getFanList(userID);
        for(Follow follow : follows){
            follow.setFollowerInfo(userMapper.getById(follow.getFollowerID()));
        }
        return follows;
    }
}
