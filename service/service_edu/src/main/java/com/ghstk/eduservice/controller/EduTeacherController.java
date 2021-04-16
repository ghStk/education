package com.ghstk.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ghstk.commonutils.Result;
import com.ghstk.eduservice.entity.EduTeacher;
import com.ghstk.eduservice.entity.vo.TeacherQuery;
import com.ghstk.eduservice.service.EduTeacherService;
import com.ghstk.servicebase.exceptionhandler.MyException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin
public class EduTeacherController {

    @Autowired
    private EduTeacherService teacherService;

    @ApiOperation("所有讲师列表")
    @GetMapping("/findAll")
    public Result findAllTeacher() {
        List<EduTeacher> list = teacherService.list(null);
        return Result.ok().data("items", list);
    }

    @ApiOperation("逻辑删除讲师")
    @DeleteMapping("/{id}")
    public Result deleteTeacher(@ApiParam(name = "id", value = "讲师ID") @PathVariable("id") String id) {
        if (teacherService.removeById(id)) {
            return Result.ok();
        } else {
            return Result.ng();
        }
    }

    @ApiOperation("查询单个讲师")
    @GetMapping("/{id}")
    public Result getTeacher(@PathVariable String id) {
        EduTeacher teacher = teacherService.getById(id);
        return Result.ok().data("teacher", teacher);
    }

    @ApiOperation("修改讲师信息")
    @PostMapping("/updateTeacher")
    public Result updateTeacher(@RequestBody EduTeacher teacher) {
        teacherService.updateById(teacher);
        return Result.ok().data("teacher", teacher);
    }


/*    @ApiOperation("分页查询讲师")
    @GetMapping("/pageTeacher/{current}/{size}")
    public Result pageListTeacher(@ApiParam("当前页码") @PathVariable long current,
                                  @ApiParam("每页数量") @PathVariable long size) {
        Page<EduTeacher> pageTeacher = new Page<>(current, size);
        teacherService.page(pageTeacher, null); // 封装分页数据
        return Result.ok()
                .data("total", pageTeacher.getTotal())
                .data("rows", pageTeacher.getRecords());
    }*/

    @ApiOperation("分页条件查询讲师")
    @PostMapping("/pageTeacher/{current}/{size}")
    public Result pageListTeacherCondition(@ApiParam("当前页码") @PathVariable long current,
                                           @ApiParam("每页数量") @PathVariable long size,
                                           @RequestBody(required = false) TeacherQuery teacherQuery) {
        // 创建page
        Page<EduTeacher> pageTeacher = new Page<>(current, size);
        // 查询条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(level)) {
            wrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_create", end);
        }
        // 根据创建时间降序,保证新建的在前面
        wrapper.orderByDesc("gmt_create");
        // 实现分页查询
        teacherService.page(pageTeacher, wrapper);
        return Result.ok()
                .data("total", pageTeacher.getTotal())
                .data("rows", pageTeacher.getRecords());
    }

    @ApiOperation("新增讲师")
    @PostMapping("/addTeacher")
    public Result addTeacher(@RequestBody EduTeacher teacher) {
        if (teacherService.save(teacher)) {
            return Result.ok();
        } else {
            return Result.ng();
        }
    }

}

