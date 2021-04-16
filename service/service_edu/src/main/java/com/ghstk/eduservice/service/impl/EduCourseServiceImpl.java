package com.ghstk.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ghstk.eduservice.entity.EduCourse;
import com.ghstk.eduservice.entity.EduCourseDescription;
import com.ghstk.eduservice.entity.EduTeacher;
import com.ghstk.eduservice.entity.frontvo.FrontCourseQuery;
import com.ghstk.eduservice.entity.vo.CoursePublishVo;
import com.ghstk.eduservice.entity.vo.CourseQuery;
import com.ghstk.eduservice.mapper.EduCourseMapper;
import com.ghstk.eduservice.service.EduChapterService;
import com.ghstk.eduservice.service.EduCourseDescriptionService;
import com.ghstk.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ghstk.eduservice.service.EduVideoService;
import com.ghstk.servicebase.exceptionhandler.MyException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author ghStk
 * @since 2021-04-02
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    EduCourseDescriptionService descService;
    @Autowired
    EduChapterService chapterService;
    @Autowired
    EduVideoService videoService;

    @Override
    public String saveCourse(CourseQuery vo) {
        // 将vo中的内容拆分,出course与desc
        EduCourse course = new EduCourse();
        EduCourseDescription courseDesc = new EduCourseDescription();
        BeanUtils.copyProperties(vo, course);
        BeanUtils.copyProperties(vo, courseDesc);

        // course与desc存入数据库
        this.save(course);
        courseDesc.setId(course.getId());
        descService.save(courseDesc);
        return course.getId();
    }

    @Override
    public CourseQuery getCourseById(String courseId) {
        // 查询
        EduCourse course = this.getById(courseId);
        EduCourseDescription desc = descService.getById(courseId);

        // 赋值
        CourseQuery vo = new CourseQuery();
        BeanUtils.copyProperties(course, vo);
        vo.setDescription(desc.getDescription());

        return vo;
    }

    @Override
    public boolean updateCourse(CourseQuery courseVo) {
        EduCourse course = new EduCourse();
        EduCourseDescription desc = new EduCourseDescription();
        BeanUtils.copyProperties(courseVo, course);
        BeanUtils.copyProperties(courseVo, desc);
        if (this.updateById(course)) {
            return descService.updateById(desc);
        }
        throw new MyException(20001, "修改课程信息失败");
    }

    @Override
    public CoursePublishVo getCoursePublish(String courseId) {
        return baseMapper.getCoursePublish(courseId);
    }

    @Override
    public boolean deleteCourseById(String courseId) {
        descService.removeById(courseId);
        videoService.removeByCourseId(courseId);
        chapterService.removeByCourseId(courseId);
        this.removeById(courseId);
        return true;
    }

    @Cacheable(value = "course", key = "'selectIndexCourse'")
    @Override
    public List<EduCourse> getIndexCourse(int limit) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("gmt_create");
        wrapper.last("limit " + limit);
        return this.list(wrapper);
    }

    @Override
    public Map<String, Object> getCourseListFront(long current, long size, FrontCourseQuery courseQuery) {
        // 创建page
        Page<EduCourse> pageParam = new Page<>(current, size);
        // 查询条件
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        if (courseQuery != null) {
            String parentId = courseQuery.getSubjectParentId();
            String subjectId = courseQuery.getSubjectId();
            String buyCountSort = courseQuery.getBuyCountSort();
            String gmtCreateSort = courseQuery.getGmtCreateSort();
            String priceSort = courseQuery.getPriceSort();

            if (!StringUtils.isEmpty(parentId)) {
                wrapper.eq("subject_parent_id", parentId);
            }
            if (!StringUtils.isEmpty(subjectId)) {
                wrapper.eq("subject_id", subjectId);
            }
            if (!StringUtils.isEmpty(buyCountSort)) {
                if ("+".equals(buyCountSort)) {
                    wrapper.orderByAsc("buy_count");
                } else {
                    wrapper.orderByDesc("buy_count");
                }
            }
            if (!StringUtils.isEmpty(gmtCreateSort)) {
                if ("+".equals(gmtCreateSort)) {
                    wrapper.orderByAsc("gmt_create");
                } else {
                    wrapper.orderByDesc("gmt_create");
                }
            }
            if (!StringUtils.isEmpty(priceSort)) {
                if ("+".equals(priceSort)) {
                    wrapper.orderByAsc("price");
                } else {
                    wrapper.orderByDesc("price");
                }
            }
        }
        // 进行分页查询
        baseMapper.selectPage(pageParam, wrapper);

        // 封装数据
        Map<String, Object> map = new HashMap<>();
        map.put("current", current);
        map.put("size", size);
        map.put("items", pageParam.getRecords());
        map.put("pages", pageParam.getPages());
        map.put("total", pageParam.getTotal());
        map.put("hasNext", pageParam.hasNext());
        map.put("hasPrevious", pageParam.hasPrevious());

        //map返回
        return map;
    }

    @Override
    public FrontCourseQuery getCourseInfoById(String courseId) {
        return baseMapper.getCourseFront(courseId);
    }
}
