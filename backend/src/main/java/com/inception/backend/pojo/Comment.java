package com.inception.backend.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private Integer commentID;
    private Integer dreamID;
    private Integer userID;
    private String commentTime;
    private String commentContent;
}
