package com.inception.backend.service;

public interface AvatarUploadService {
    void avatarUpload(String fileURL,String userName,String userSignature,Integer userID);
    void userNameUpload(String userName,Integer userID);
    void userSignatureUpload(String userSignature,Integer userID);
}
