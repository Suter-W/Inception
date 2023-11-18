package com.inception.backend.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UploadMapper {
    @Update("update user set userAvatar = #{fileURL},userName = #{userName},userSignature = #{userSignature} where userID = #{userID}")
    void upload(String fileURL,String userName,String userSignature,Integer userID);

    @Update("update user set userName = #{userName} where userID = #{userID}")
    void uploadName(String userName,Integer userID);

    @Update("update user set userSignature = #{userSignature} where userID = #{userID}")
    void uploadSignature(String userSignature,Integer userID);
}
