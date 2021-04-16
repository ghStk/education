package com.ghstk.eduservice.controller;


import com.ghstk.commonutils.Result;
import com.ghstk.eduservice.entity.EduSubject;
import com.ghstk.eduservice.entity.vo.SubjectQuery;
import com.ghstk.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author ghStk
 * @since 2021-03-31
 */
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService subjectService;

    @PostMapping("/addSubject")
    public Result addSubject(MultipartFile file) {
        subjectService.saveSubject(file);
        return Result.ok().message("上传成功");
    }

    @GetMapping("/getSubjectList")
    public Result getSubjectList() {
        ArrayList<SubjectQuery> result = new ArrayList<>();
        Map<String, Integer> index = new HashMap<>();
        List<EduSubject> list = subjectService.list(null);

        // 添加一级分类
        int i = 0;
        for (EduSubject s : list) {
            if (s.getParentId().equals("0")) {
                index.put(s.getId(), i);
                result.add(new SubjectQuery(s.getId(), s.getTitle()));
                i++;
            }
        }

        // 添加二级分类
        for (EduSubject s : list) {
            if (!s.getParentId().equals("0")) { //不是1级分类
                String pId = s.getParentId();
                Integer pIndex;
                if ((pIndex = index.get(pId)) != null) {
                    result.get(pIndex).getChildren()
                            .add(new SubjectQuery(s.getId(), s.getTitle()));
                }
            }
        }

        return Result.ok().data("rows", result);
    }

}

