package com.ghstk.eduservice.service;

import com.ghstk.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ghstk.eduservice.entity.vo.TeacherQuery;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author ghStk
 * @since 2021-03-25
 */
public interface EduTeacherService extends IService<EduTeacher> {

    List<EduTeacher> getIndexTeacher(int limit);


    Map<String, Object> getTeacherListFront(long current, long size, TeacherQuery teacherQuery);
}
