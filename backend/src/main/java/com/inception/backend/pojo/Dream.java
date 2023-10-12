package com.inception.backend.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dream {
    private Integer dreamID;

    private Integer userID;

    private String dreamContent;

    private String dreamTime;

    private short dreamStatus;

    private Integer likeCount;
}
