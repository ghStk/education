package com.ghstk.eduservice.mapper;

import com.ghstk.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ghstk.eduservice.entity.frontvo.FrontCourseQuery;
import com.ghstk.eduservice.entity.vo.CoursePublishVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author ghStk
 * @since 2021-04-02
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    CoursePublishVo getCoursePublish(String courseId);

    FrontCourseQuery getCourseFront(String courseId);

}
