package com.inception.backend.controller;


import com.inception.backend.pojo.Dream;
import com.inception.backend.pojo.Result;
import com.inception.backend.service.DreamPublishService;
import jakarta.annotation.security.PermitAll;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Publish")
@PermitAll
@CrossOrigin
@Slf4j
public class DreamPublishController {

    @Autowired
    private DreamPublishService dreamPublishService;

    @PostMapping("/publishDream")
    public Result publishDream(@RequestBody Dream dream){
        log.info("用户发布梦境,用户：",dream.getUserID());
        dreamPublishService.publishDream(dream);
        return Result.success();
    }
}
