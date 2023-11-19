package com.inception.backend.service;

import com.inception.backend.pojo.Follow;
import com.inception.backend.pojo.User;

import java.util.List;

public interface FollowService {
    Boolean isFollow(Integer userID, Integer hostID);

    void createFollow(Integer userID, Integer hostID);

    void cancelFollow(Integer userID, Integer hostID);

    User getHostInfo(Integer hostID);

    List<Follow> getFollowList(Integer userID);

    List<Follow> getFanList(Integer userID);
}
