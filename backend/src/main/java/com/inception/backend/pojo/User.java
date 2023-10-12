package com.inception.backend.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer userID;

    private String userPhoneNum;

    private String userPassword;

    private String userName;

    private String userAvatar;

}
