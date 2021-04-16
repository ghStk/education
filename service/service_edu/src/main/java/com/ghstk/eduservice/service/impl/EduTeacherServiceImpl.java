package com.ghstk.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ghstk.eduservice.entity.EduTeacher;
import com.ghstk.eduservice.entity.vo.TeacherQuery;
import com.ghstk.eduservice.mapper.EduTeacherMapper;
import com.ghstk.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author ghStk
 * @since 2021-03-25
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Cacheable(value = "teacher",key = "'selectIndexTeacher'")
    @Override
    public List<EduTeacher> getIndexTeacher(int limit) {

        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("gmt_create");
        wrapper.last("limit " + limit);
        return this.list(wrapper);
    }

    @Override
    public Map<String, Object> getTeacherListFront(long current, long size, TeacherQuery teacherQuery) {
        // 创建page
        Page<EduTeacher> pageParam = new Page<>(current, size);
        // 查询条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        if (teacherQuery != null) {
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
        }
        // 根据创建时间降序,保证新建的在前面
        wrapper.orderByDesc("gmt_create");
        // 实现分页查询
        baseMapper.selectPage(pageParam, wrapper);
        // 封装数据
        Map<String, Object> map = new HashMap<>();
        map.put("current", current);
        map.put("size", size);
        map.put("items", pageParam.getRecords());
        map.put("pages", pageParam.getPages()); //总页数
        map.put("total", pageParam.getTotal()); //当前页总记录数
        map.put("hasNext", pageParam.hasNext());
        map.put("hasPrevious", pageParam.hasPrevious());

        return map;
    }
}
