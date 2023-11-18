package com.inception.backend.service.serviceImpl;

import com.inception.backend.mapper.DreamWorldMapper;
import com.inception.backend.pojo.*;
import com.inception.backend.service.DreamWorldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class DreamWorldServiceImpl implements DreamWorldService {

    @Autowired
    private DreamWorldMapper dreamWorldMapper;

    @Override
    public List<Dream> getDreamList() {
        return dreamWorldMapper.getAllDreams();
    }

    @Override
    public List<Dream> getDreamLog(Integer userID) {
        return dreamWorldMapper.getDreamLog(userID);
    }

    @Override
    public List<Like> userLikeList(Integer userID) {
        return dreamWorldMapper.getLikeByUser(userID);
    }

    @Override
    public List<Favorite> userFavoriteList(Integer userID) {
        return dreamWorldMapper.getFavoriteByUser(userID);
    }

    @Override
    public void like(Integer dreamID, Integer userID) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String likeTime = LocalDateTime.now().format(formatter);
        dreamWorldMapper.addLike(dreamID,userID,likeTime);
        dreamWorldMapper.likeCountPlus(dreamID);
    }

    @Override
    public void cancelLike(Integer dreamID, Integer userID) {
        dreamWorldMapper.deleteLike(dreamID,userID);
        dreamWorldMapper.likeCountMinus(dreamID);
    }

    @Override
    public void favorite(Integer dreamID, Integer userID) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String favoriteTime = LocalDateTime.now().format(formatter);
        dreamWorldMapper.addFavorite(dreamID,userID,favoriteTime);
    }

    @Override
    public void cancelFavorite(Integer dreamID, Integer userID) {
        dreamWorldMapper.deleteFavorite(dreamID,userID);
    }

    @Override
    public List<User> getUserInfoByIDs(List<Integer> userIDs) {
        List<User> users = new ArrayList<>();
        for (Integer userID : userIDs){
            User user = dreamWorldMapper.getUserInfoByID(userID);
            users.add(user);
        }
        return users;
    }

    @Override
    public List<Comment> getDreamCommentList(Integer dreamID) {
        return dreamWorldMapper.getDreamCommentList(dreamID);
    }

    @Override
    public void publishComment(Comment comment) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String commentTime = LocalDateTime.now().format(formatter);
        comment.setCommentTime(commentTime);
        dreamWorldMapper.publishComment(comment);
    }

    @Override
    public Dream getDreamByDreamId(Integer dreamId) {
        return dreamWorldMapper.getDreamByDreamId(dreamId);
    }

    @Override
    public void deleteDreamByDreamId(Integer dreamID){
        dreamWorldMapper.deleteCommentDreamByDreamId(dreamID);
        dreamWorldMapper.deleteFavoriteDreamByDreamId(dreamID);
        dreamWorldMapper.deleteLikeDreamByDreamId(dreamID);
        dreamWorldMapper.deleteDreamByDreamId(dreamID);
    }

    @Override
    public void updateDreamByDreamId(String dreamContent,Integer dreamID){
        dreamWorldMapper.updateDreamByDreamId(dreamContent,dreamID);
    }

}
