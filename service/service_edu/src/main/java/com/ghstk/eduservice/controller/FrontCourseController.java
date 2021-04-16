package com.ghstk.eduservice.controller;


import com.ghstk.commonutils.Result;
import com.ghstk.eduservice.entity.frontvo.FrontCourseQuery;
import com.ghstk.eduservice.entity.vo.chapter.ChapterVo;
import com.ghstk.eduservice.service.EduChapterService;
import com.ghstk.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author ghStk
 * @since 2021-04-02
 */
@RestController
@RequestMapping("/eduservice/coursefront")
@CrossOrigin
public class FrontCourseController {

    @Autowired
    EduCourseService courseService;
    @Autowired
    EduChapterService chapterService;

    // 查询课程列表
    @PostMapping("/getFrontCourseList/{current}/{size}")
    public Result getCourseList(@RequestBody(required = false) FrontCourseQuery query,
                                @PathVariable long current, @PathVariable long size) {
        Map<String, Object> map = courseService.getCourseListFront(current, size, query);
        return Result.ok().data(map);
    }

    // 查询课程信息
    @GetMapping("/getFrontCourseInfo/{courseId}")
    public Result getCourseInfo(@PathVariable String courseId) {
        List<ChapterVo> chapterList = chapterService.getChapterList(courseId);
        FrontCourseQuery courseInfo = courseService.getCourseInfoById(courseId);

        return Result.ok()
                .data("chapterList", chapterList)
                .data("course", courseInfo);
    }

}

