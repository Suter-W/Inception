package com.inception.backend.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Favorite {

    private Integer favoriteID;

    private Integer userID;

    private Integer dreamID;

    private String favoriteTime;
}
