package com.ghstk.oss.controller;


import com.ghstk.commonutils.Result;
import com.ghstk.oss.service.OssService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduoss/fileoss")
@CrossOrigin
public class OssController {

    @Autowired
    OssService ossService;

    @PostMapping
    public Result uploadOssFile(MultipartFile file) {
        String url =  ossService.uploadFile(file);
        return Result.ok().data("url",url);
    }

}
