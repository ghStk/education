package com.ghstk.vod.controller;

import com.ghstk.commonutils.Result;
import com.ghstk.vod.service.VodService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/vodservice")
public class VodController {

    @Autowired
    VodService service;

    // 上传视频
    @PostMapping("/upload")
    public Result uploadVideo(MultipartFile file) {
        String fileId = service.uploadFile(file);
        return Result.ok().data("id", fileId);
    }

    // 删除视频
    @DeleteMapping("/{vodId}")
    public Result deleteVideo(@PathVariable String vodId) {
        if (service.deleteFile(vodId)) {
            return Result.ok();
        }
        return Result.ng().message("删除视频失败");
    }

    // 删除视频 批量
    @DeleteMapping("/deleteVideoBatch")
    public Result deleteVideoBatch(@RequestBody List<String> vodIdList) {
        String vodIds = StringUtils.join(vodIdList, ',');
        if (service.deleteFile(vodIds)) {
            return Result.ok();
        }
        return Result.ng().message("删除视频失败");
    }

    // 获取视频信息
    @GetMapping("/{vodId}")
    public Result getVideoInfo(@PathVariable String vodId) {
        String title = service.getFileInfo(vodId);
        System.out.println(title);
        return Result.ok().data("title", title);
    }

    // 获取视频凭证
    @GetMapping("/getVideoAuth/{vodId}")
    public Result getVideoAuth(@PathVariable String vodId) {
        String videoAuth = service.getVideoAuth(vodId);
        return Result.ok().data("auth", videoAuth);
    }
}
