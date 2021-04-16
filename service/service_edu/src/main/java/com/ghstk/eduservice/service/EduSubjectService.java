package com.ghstk.eduservice.service;

import com.ghstk.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author ghStk
 * @since 2021-03-31
 */
public interface EduSubjectService extends IService<EduSubject> {

    void saveSubject(MultipartFile file);
}
