package com.inception.backend.mapper;

import com.inception.backend.pojo.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DreamWorldMapper {
    @Select("select * from dream where dreamStatus != 2 order by dreamTime desc")
    List<Dream> getAllDreams();

    @Select("select * from dream where userID = #{userID} order by dreamTime desc")
    List<Dream> getDreamLog(Integer userID);

    @Select("select * from `like` where userID = #{userID}")
    List<Like> getLikeByUser(Integer userID);

    @Select("select * from favorite where userID = #{userID} order by favoriteTime desc")
    List<Favorite> getFavoriteByUser(Integer userID);

    @Insert("insert into `like` (userID, dreamID, likeTime) values (#{userID},#{dreamID},#{likeTime})")
    void addLike(Integer dreamID, Integer userID,String likeTime);

    @Update("update dream set likeCount = likeCount + 1 where dreamID = #{dreamID}")
    void likeCountPlus(Integer dreamID);

    @Delete("delete from `like` where dreamID = #{dreamID} and userID = #{userID}")
    void deleteLike(Integer dreamID, Integer userID);

    @Update("update dream set likeCount = likeCount - 1 where dreamID = #{dreamID}")
    void likeCountMinus(Integer dreamID);

    @Update("update dream set dreamStatus = #{dreamStatus},dreamContent = #{dreamContent} where dreamID = #{dreamID}")
    void updateDreamByDreamId(Integer dreamStatus,String dreamContent,Integer dreamID);
    @Insert("insert into favorite (userID, dreamID, favoriteTime) values (#{userID},#{dreamID},#{favoriteTime})")
    void addFavorite(Integer dreamID, Integer userID, String favoriteTime);

    @Delete("delete from favorite where dreamID = #{dreamID} and userID = #{userID}")
    void deleteFavorite(Integer dreamID, Integer userID);

    @Select("select * from user where userID = #{userID}")
    User getUserInfoByID(Integer userID);

    @Select("select * from comment where dreamID = #{dreamID}")
    List<Comment> getDreamCommentList(Integer dreamID);

    @Insert("insert into comment (dreamID, userID, commentTime, commentContent) values (#{dreamID},#{userID},#{commentTime},#{commentContent})")
    void publishComment(Comment comment);

    @Select("select * from dream where dreamID = #{dreamId}")
    Dream getDreamByDreamId(Integer dreamId);

    @Delete("delete from dream where dreamID = #{dreamID}")
    void deleteDreamByDreamId(Integer dreamID);

    @Delete("delete from comment where dreamID = #{dreamID}")
    void deleteCommentDreamByDreamId(Integer dreamID);

    @Delete("delete from favorite where dreamID = #{dreamID}")
    void deleteFavoriteDreamByDreamId(Integer dreamID);

    @Delete("delete from `like` where dreamID = #{dreamID}")
    void deleteLikeDreamByDreamId(Integer dreamID);
}

