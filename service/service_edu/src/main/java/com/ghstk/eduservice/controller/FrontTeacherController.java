package com.ghstk.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ghstk.commonutils.Result;
import com.ghstk.eduservice.entity.EduCourse;
import com.ghstk.eduservice.entity.EduTeacher;
import com.ghstk.eduservice.entity.vo.TeacherQuery;
import com.ghstk.eduservice.service.EduCourseService;
import com.ghstk.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api("讲师前台显示")
@RestController
@RequestMapping("/eduservice/teacherfront")
@CrossOrigin
public class FrontTeacherController {

    @Autowired
    private EduTeacherService teacherService;
    @Autowired
    private EduCourseService courseService;

    @ApiOperation("分页条件查询讲师")
    @GetMapping("/pageTeacher/{current}")
    public Result getTeacherMapCondition(@ApiParam("当前页码") @PathVariable long current,
                                         @RequestBody(required = false) TeacherQuery teacherQuery) {
        long size = 8; //页面UI效果要求
        Map<String, Object> currentPageMap = teacherService.getTeacherListFront(current, size, teacherQuery);
        return Result.ok().data(currentPageMap);
    }

    @ApiOperation("查询讲师信息")
    @GetMapping("/getTeacher/{teacherId}")
    public Result getTeacher(@PathVariable String teacherId) {
        EduTeacher teacher = teacherService.getById(teacherId);
        List<EduCourse> courseList = courseService.list(new QueryWrapper<EduCourse>().eq("teacher_id", teacherId));
        return Result.ok()
                .data("teacher", teacher)
                .data("courseList", courseList);
    }

}

