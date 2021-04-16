package com.ghstk.eduservice.controller;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ghstk.eduservice.entity.EduSubject;
import com.ghstk.eduservice.entity.excel.SubjectData;
import com.ghstk.eduservice.service.EduSubjectService;
import com.ghstk.servicebase.exceptionhandler.MyException;

public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    private final EduSubjectService service;

    public SubjectExcelListener(EduSubjectService service) {
        this.service = service;
    }

    @Override
    public void invoke(SubjectData data, AnalysisContext context) {
        if (data == null) {
            throw new MyException(20001, "读取excel为空");
        }
        EduSubject subjectLv1 = getSubjectLv1(data.getLv1());
        EduSubject subjectLv2 = getSubjectLv2(data.getLv2(), subjectLv1.getId());
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }

    // 获取一级分类(无则创建)
    private EduSubject getSubjectLv1(String name) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", "0");
        EduSubject subject = service.getOne(wrapper);
        if (subject == null) {
            subject = new EduSubject();
            subject.setTitle(name);
            service.save(subject);
        }
        return subject;
    }

    // 获取二级分类(无则创建)
    private EduSubject getSubjectLv2(String name, String pid) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", pid);
        EduSubject subject = service.getOne(wrapper);
        if (subject == null) {
            subject = new EduSubject();
            subject.setTitle(name);
            subject.setParentId(pid);
            service.save(subject);
        }
        return subject;
    }


}
