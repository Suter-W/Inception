package com.inception.backend.controller;

import com.inception.backend.pojo.AvatarRequest;
import com.inception.backend.pojo.Result;
import com.inception.backend.service.AvatarUploadService;
import com.inception.backend.util.OssUploadUtil;
import jakarta.annotation.security.PermitAll;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/Upload")
@PermitAll
@CrossOrigin
@Slf4j
public class AvatarUploadController {
    @Autowired
    private AvatarUploadService avatarUploadService;

    @PostMapping("/upload")
    public Result avatarUpload(@RequestParam("file") MultipartFile file,@RequestParam("userName") String userName,@RequestParam("userSignature") String userSignature,@RequestParam("userID") Integer userID) throws IOException {
        if(file.isEmpty()){
            return Result.error("上传失败！");
        }
        else{
            String fileURL = OssUploadUtil.uploadImage(file);
            avatarUploadService.avatarUpload(fileURL,userName,userSignature,userID);
        }
        return Result.success();
    }
}

