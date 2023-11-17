package com.inception.backend.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvatarRequest {

    private MultipartFile file;

    private String userName;

    private String userSignature;

    private Integer userID;

}
