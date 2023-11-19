package com.inception.backend.mapper;

import com.inception.backend.pojo.Follow;
import com.inception.backend.pojo.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FollowMapper {
    @Select("select * from follow where followerID = #{userID} and followeeID = #{hostID}")
    List<Follow> isFollow(Integer userID, Integer hostID);

    @Insert("insert into follow (followerID, followeeID, followTime) values (#{userID},#{hostID},#{formattedDateTime})")
    void Follow(Integer userID, Integer hostID, String formattedDateTime);

    @Delete("delete from follow where followerID = #{userID} and followeeID = #{hostID}")
    void cancelFollow(Integer userID, Integer hostID);

    @Select("select * from user where userID = #{hostID}")
    User getHostInfo(Integer hostID);

    @Select("select * from follow where followerID = #{userID}")
    List<Follow> getFollowList(Integer userID);
    @Select("select * from follow where followeeID = #{userID}")
    List<Follow> getFanList(Integer userID);
}
