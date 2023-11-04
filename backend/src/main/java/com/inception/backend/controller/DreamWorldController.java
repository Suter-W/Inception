package com.inception.backend.controller;


import com.inception.backend.pojo.*;
import com.inception.backend.service.DreamWorldService;
import jakarta.annotation.security.PermitAll;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/World")
@PermitAll
@CrossOrigin
@Slf4j
public class DreamWorldController {

    @Autowired
    private DreamWorldService dreamWorldService;

    private List<User> getUserInfoByIDs(List<Integer> userIDs){
        List<User> users = dreamWorldService.getUserInfoByIDs(userIDs);
        return users;
    }

    @GetMapping("/getDreamLog")
    public Result getDreamLog(@RequestParam Integer userID) {
        log.info("获取该用户所有梦境记录");
        List<Dream> dreams = dreamWorldService.getDreamLog(userID);
        List<Like> likes = dreamWorldService.userLikeList(userID);
        List<Favorite> favorites = dreamWorldService.userFavoriteList(userID);
        List<Integer> likeDreamIDs = new ArrayList<>();
        List<Integer> favoriteDreamIDs = new ArrayList<>();
        List<Integer> userIDs = new ArrayList<>();
        for (Like like : likes) {
            likeDreamIDs.add(like.getDreamID());
        }
        for(Favorite favorite : favorites){
            favoriteDreamIDs.add(favorite.getDreamID());
        }
        for(Dream dream : dreams){
            if(likeDreamIDs.contains(dream.getDreamID())){
                dream.setIsLike(true);
            }
            if(favoriteDreamIDs.contains(dream.getDreamID())){
                dream.setIsFavorite(true);
            }
            if(!userIDs.contains(dream.getUserID())){
                userIDs.add(dream.getUserID());
            }
        }
        List<User> users = getUserInfoByIDs(userIDs);
        for(Dream dream : dreams){
            for(User user : users){
                if(Objects.equals(dream.getUserID(), user.getUserID())){
                    dream.setUserName(user.getUserName());
                    dream.setUserAvatar(user.getUserAvatar());
                }
            }
        }
        return Result.success(dreams);
    }

    /**
     * 打开主页时取出所有梦境,并且判断每个梦境是否被该用户收藏或点赞
     * @return
     */
    @GetMapping("/getDreamList")
    public Result getDreamList(@RequestParam Integer userID) {
        log.info("获取所有公开梦境,并且获取每个梦境该用户是否点赞或收藏");
        List<Dream> dreams = dreamWorldService.getDreamList();
        List<Like> likes = dreamWorldService.userLikeList(userID);
        List<Favorite> favorites = dreamWorldService.userFavoriteList(userID);
        List<Integer> likeDreamIDs = new ArrayList<>();
        List<Integer> favoriteDreamIDs = new ArrayList<>();
        List<Integer> userIDs = new ArrayList<>();
        for (Like like : likes) {
            likeDreamIDs.add(like.getDreamID());
        }
        for(Favorite favorite : favorites){
            favoriteDreamIDs.add(favorite.getDreamID());
        }
        for(Dream dream : dreams){
            if(likeDreamIDs.contains(dream.getDreamID())){
                dream.setIsLike(true);
            }
            if(favoriteDreamIDs.contains(dream.getDreamID())){
                dream.setIsFavorite(true);
            }
            if(!userIDs.contains(dream.getUserID())){
                userIDs.add(dream.getUserID());
            }
        }
        List<User> users = getUserInfoByIDs(userIDs);
        for(Dream dream : dreams){
            for(User user : users){
                if(Objects.equals(dream.getUserID(), user.getUserID())){
                    dream.setUserName(user.getUserName());
                    dream.setUserAvatar(user.getUserAvatar());
                }
            }
        }
        return Result.success(dreams);
    }

    @GetMapping("/like")
    public Result Like(@RequestParam Integer dreamID,
                       @RequestParam Integer userID){
        log.info("用户点赞，用户ID："+userID);
        dreamWorldService.like(dreamID,userID);
        return Result.success();
    }

    @GetMapping("/cancelLike")
    public Result cancelLike(@RequestParam Integer dreamID,
                             @RequestParam Integer userID){
        log.info("用户取消点赞");
        dreamWorldService.cancelLike(dreamID,userID);
        return  Result.success();
    }

    @GetMapping("/favorite")
    public Result Favorite(@RequestParam Integer dreamID,
                           @RequestParam Integer userID){
        log.info("用户收藏，用户ID："+userID);
        dreamWorldService.favorite(dreamID,userID);
        return Result.success();
    }

    @GetMapping("/cancelFavorite")
    public Result cancelFavorite(@RequestParam Integer dreamID,
                                 @RequestParam Integer userID){
        log.info("用户取消收藏");
        dreamWorldService.cancelFavorite(dreamID,userID);
        return Result.success();
    }

    @GetMapping("/getDreamCommentList")
    public Result getDreamCommentList(@RequestParam Integer dreamID){
        log.info("获取梦境的评论");
        List<Comment> commentList = dreamWorldService.getDreamCommentList(dreamID);
        return Result.success(commentList);
    }

    @PostMapping("/publishComment")
    public Result publishComment(@RequestBody Comment comment){
        log.info("新增评论：{}",comment);
        dreamWorldService.publishComment(comment);
        return Result.success();
    }

}
