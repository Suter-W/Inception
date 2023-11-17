package com.inception.backend.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UploadMapper {
    @Update("update user set userAvatar = #{fileURL} where userID = #{userID}")
    void upload(String fileURL,Integer userID);
}
