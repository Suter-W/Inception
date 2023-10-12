package com.inception.backend.service.serviceImpl;

import com.inception.backend.mapper.DreamPublishMapper;
import com.inception.backend.pojo.Dream;
import com.inception.backend.service.DreamPublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DreamPublishServiceImpl implements DreamPublishService {

    @Autowired
    private DreamPublishMapper dreamPublishMapper;
    public void publishDream(Dream dream) {
        dreamPublishMapper.dreamPublish(dream);
    }
}
