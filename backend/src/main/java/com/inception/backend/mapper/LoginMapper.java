package com.inception.backend.mapper;

import com.inception.backend.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LoginMapper {
    /**
     * 根据用户手机号和密码查询用户
     * @param userPhoneNum
     * @param userPassword
     * @return
     */
    @Select("select * from user where userPhoneNum = #{userPhoneNum} and userPassword = #{userPassword}")
    User getByUserPhoneNumAndPassword(String userPhoneNum, String userPassword);

    @Insert("insert into user (userPhoneNum, userPassword, userName) values (#{userPhoneNum}, #{userPassword}, #{userName})")
    void register(User user);

    @Select("select * from user where userPhoneNum = #{userPhoneNum}")
    User getByUserPhoneNum(String userPhoneNum);
}
