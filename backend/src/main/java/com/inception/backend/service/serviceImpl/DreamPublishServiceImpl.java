package com.inception.backend.service.serviceImpl;

import com.inception.backend.mapper.DreamPublishMapper;
import com.inception.backend.pojo.Dream;
import com.inception.backend.service.DreamPublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class DreamPublishServiceImpl implements DreamPublishService {

    @Autowired
    private DreamPublishMapper dreamPublishMapper;
    public void publishDream(Dream dream) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String publishTime = LocalDateTime.now().format(formatter);
        dream.setDreamTime(publishTime);
        dreamPublishMapper.dreamPublish(dream);
    }
}
