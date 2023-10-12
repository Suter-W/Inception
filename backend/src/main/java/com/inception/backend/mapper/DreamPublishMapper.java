package com.inception.backend.mapper;

import com.inception.backend.pojo.Dream;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DreamPublishMapper {
    @Insert("insert into dream (userID,dreamContent,dreamTime,dreamStatus,likeCount) " +
            "values (#{userID},#{dreamContent},#{dreamTime},#{dreamStatus},0)")
    void dreamPublish(Dream dream);
}
