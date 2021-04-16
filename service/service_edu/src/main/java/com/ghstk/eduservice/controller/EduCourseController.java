package com.ghstk.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ghstk.commonutils.Result;
import com.ghstk.eduservice.entity.EduCourse;
import com.ghstk.eduservice.entity.vo.CoursePublishVo;
import com.ghstk.eduservice.entity.vo.CourseQuery;
import com.ghstk.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author ghStk
 * @since 2021-04-02
 */
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    EduCourseService service;

    // 添加课程
    @PostMapping("/addCourse")
    public Result addCourse(@RequestBody CourseQuery course) {
        String courseId;
        if ((courseId = service.saveCourse(course)) != null) {
            return Result.ok().message("添加课程成功").data("courseId", courseId);
        }
        return Result.ng().message("添加课程出错");
    }

    // 删除课程
    @DeleteMapping("/{courseId}")
    public Result deleteCourse(@PathVariable String courseId) {
        if (service.deleteCourseById(courseId)) {
            return Result.ok();
        }
        return Result.ng().message("删除失败");
    }

    // 修改课程
    @PostMapping
    public Result updateCourse(@RequestBody CourseQuery courseVo) {
        if (service.updateCourse(courseVo)) {
            return Result.ok();
        } else {
            return Result.ng().message("修改失败");
        }
    }

    // 发布课程
    @PostMapping("/publish/{courseId}")
    public Result publishCourse(@PathVariable String courseId) {
        EduCourse course = new EduCourse();
        course.setId(courseId);
        course.setStatus("Normal");
        if (service.updateById(course)) {
            return Result.ok();
        }
        return Result.ng().message("发布失败");
    }

    // 查询课程
    @GetMapping("/{courseId}")
    public Result getCourse(@PathVariable("courseId") String courseId) {
        CourseQuery courseVo = service.getCourseById(courseId);
        return Result.ok().data("courseVo", courseVo);
    }

    // 查询课程列表
    @PostMapping("/getList/{current}/{size}")
    public Result getCourseList(@RequestBody(required = false) CourseQuery courseQuery,
                                @PathVariable long current, @PathVariable long size) {
        // 创建page
        Page<EduCourse> page = new Page<>(current, size);
        // 查询条件
        QueryWrapper<EduCourse> wrapper = null;
        if (courseQuery != null) {
            wrapper = new QueryWrapper<>();
            if (!StringUtils.isEmpty(courseQuery.getTitle())) {
                wrapper.like("title", courseQuery.getTitle());
            }
            if (!StringUtils.isEmpty(courseQuery.getStatus())) {
                wrapper.eq("status", courseQuery.getStatus());
            }
        }
        // 进行分页查询
        service.page(page, wrapper);
        return Result.ok()
                .data("rows", page.getRecords())
                .data("total", page.getTotal());
    }

    // 发布前确认信息
    @GetMapping("/publish/{courseId}")
    public Result getCoursePublish(@PathVariable String courseId) {
        CoursePublishVo coursePublishVo = service.getCoursePublish(courseId);
        return Result.ok().data("item", coursePublishVo);
    }


}

