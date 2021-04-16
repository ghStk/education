package com.ghstk.eduservice.service;

import com.ghstk.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author ghStk
 * @since 2021-04-02
 */
public interface EduVideoService extends IService<EduVideo> {

    boolean removeByCourseId(String courseId);
}
