package com.ghstk.eduservice.controller;


import com.ghstk.commonutils.Result;
import com.ghstk.eduservice.client.VodClient;
import com.ghstk.eduservice.entity.EduChapter;
import com.ghstk.eduservice.entity.vo.chapter.ChapterVo;
import com.ghstk.eduservice.service.EduChapterService;
import com.ghstk.servicebase.exceptionhandler.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author ghStk
 * @since 2021-04-02
 */
@RestController
@RequestMapping("/eduservice/chapter")
@CrossOrigin
public class EduChapterController {

    @Autowired
    EduChapterService service;
    @Autowired
    VodClient vodClient;

    @PostMapping("/addChapter")
    public Result saveChapter(@RequestBody EduChapter chapter) {
        service.save(chapter);
        return Result.ok();
    }

    @DeleteMapping("/{chapterId}")
    public Result deleteChapter(@PathVariable String chapterId) {
        if (service.deleteChapter(chapterId)) {
            return Result.ok();
        } else {
            return Result.ng();
        }
    }

    @PostMapping
    public Result UpdateChapter(@RequestBody EduChapter chapter) {
        if (chapter.getId() != null) {
            service.updateById(chapter);
            return Result.ok();
        } else {
            throw new MyException(20001, "章节id不能为空");
        }
    }

    @GetMapping("/{courseId}")
    public Result getChapterList(@PathVariable String courseId) {
        List<ChapterVo> chapterList = service.getChapterList(courseId);
        return Result.ok().data("rows", chapterList);
    }

    @GetMapping("/getChapter/{chapterId}")
    public Result getChapterById(@PathVariable String chapterId) {
        EduChapter chapter = service.getById(chapterId);
        return Result.ok().data("item", chapter);
    }


}

