package com.inception.backend.mapper;


import com.inception.backend.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("select * from user where userID = #{id}")
    User getById(Integer id);
}
