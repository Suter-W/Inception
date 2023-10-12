package com.inception.backend.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Like {

    private Integer likeID;

    private Integer userID;

    private Integer dreamID;

    private String likeTime;
}
