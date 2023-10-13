package com.inception.backend.service;

import com.inception.backend.pojo.Dream;
import com.inception.backend.pojo.Favorite;
import com.inception.backend.pojo.Like;
import com.inception.backend.pojo.User;

import java.util.List;

public interface DreamWorldService {
    List<Dream> getDreamList();

    List<Like> userLikeList(Integer userID);

    List<Favorite> userFavoriteList(Integer userID);

    void like(Integer dreamID, Integer userID);

    void cancelLike(Integer dreamID, Integer userID);

    void favorite(Integer dreamID, Integer userID);

    void cancelFavorite(Integer dreamID, Integer userID);

    List<User> getUserInfoByIDs(List<Integer> userIDs);
}
