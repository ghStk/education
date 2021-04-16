package com.ghstk.eduservice.controller;


import com.ghstk.commonutils.Result;
import com.ghstk.eduservice.client.VodClient;
import com.ghstk.eduservice.entity.EduVideo;
import com.ghstk.eduservice.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author ghStk
 * @since 2021-04-02
 */
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {

    @Autowired
    EduVideoService service;
    @Autowired
    VodClient vodClient;

    /**
     * 添加小节
     */
    @PostMapping("/addVideo")
    public Result addVideo(@RequestBody EduVideo video) {
        service.save(video);
        return Result.ok();
    }

    /**
     * 删除小节,及其视频
     */
    @DeleteMapping("/{videoId}")
    public Result deleteVideo(@PathVariable String videoId) {
        EduVideo video = service.getById(videoId);
        String vodId = video.getVideoSourceId();
        // 删除云端视频
        if (vodId!=null){
            Result r = vodClient.deleteVideo(vodId);
            if (r.getCode()!=20000){
                return Result.ng().message("熔断器:删除失败");
            }
        }
        // 删除小节
        if (service.removeById(videoId)) {
            return Result.ok();
        } else {
            return Result.ng().message("删除失败");
        }
    }

    @PostMapping
    public Result updateVideo(@RequestBody EduVideo video) {
        service.updateById(video);
        return Result.ok();
    }

    @GetMapping("/{videoId}")
    public Result getVideoById(@PathVariable String videoId) {
        EduVideo video = service.getById(videoId);
        return Result.ok().data("item", video);
    }
}

