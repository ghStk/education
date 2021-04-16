package com.ghstk.eduservice.controller;

import com.ghstk.commonutils.Result;
import com.ghstk.eduservice.entity.EduCourse;
import com.ghstk.eduservice.entity.EduTeacher;
import com.ghstk.eduservice.service.EduCourseService;
import com.ghstk.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/eduservice/index")
public class IndexController {

    @Autowired
    EduTeacherService teacherService;
    @Autowired
    EduCourseService courseService;

    @GetMapping
    public Result getIndex() {
        List<EduTeacher> indexTeacher = teacherService.getIndexTeacher(4);
        List<EduCourse> indexCourse = courseService.getIndexCourse(8);
        return Result.ok()
                .data("teacherList", indexTeacher)
                .data("courseList", indexCourse);
    }

}
