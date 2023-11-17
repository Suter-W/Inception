package com.inception.backend.service.serviceImpl;


import com.inception.backend.mapper.DreamPublishMapper;
import com.inception.backend.mapper.UploadMapper;
import com.inception.backend.service.AvatarUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AvatarUploadServiceImpl implements AvatarUploadService {

    @Autowired
    private UploadMapper uploadMapper;

    @Override
    public void avatarUpload(String fileURL,String userName,String userSignature,Integer userID) {
        uploadMapper.upload(fileURL,userName,userSignature,userID);
    }
}
