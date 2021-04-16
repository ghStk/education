package com.ghstk.eduservice.service;

import com.ghstk.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ghstk.eduservice.entity.frontvo.FrontCourseQuery;
import com.ghstk.eduservice.entity.vo.CoursePublishVo;
import com.ghstk.eduservice.entity.vo.CourseQuery;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author ghStk
 * @since 2021-04-02
 */
public interface EduCourseService extends IService<EduCourse> {


    /**
     * 保存课程信息
     *
     * @param vo 包含course与desc信息
     * @return 对新course生成的id
     */
    String saveCourse(CourseQuery vo);

    /**
     * 获取课程信息
     *
     * @param courseId 课程id
     * @return vo对象
     */
    CourseQuery getCourseById(String courseId);

    boolean updateCourse(CourseQuery courseVo);

    CoursePublishVo getCoursePublish(String courseId);

    boolean deleteCourseById(String courseId);

    List<EduCourse> getIndexCourse(int limit);

    Map<String, Object> getCourseListFront(long current, long size, FrontCourseQuery courseQuery);

    FrontCourseQuery getCourseInfoById(String courseId);
}
