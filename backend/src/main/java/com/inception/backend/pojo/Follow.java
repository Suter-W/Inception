package com.inception.backend.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Follow {
    private Integer followID;

    private Integer followerID;

    private Integer followeeID;

    private String followTime;

    private User followerInfo;

    private User followeeInfo;
}
