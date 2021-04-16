package com.ghstk.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.ghstk.eduservice.controller.SubjectExcelListener;
import com.ghstk.eduservice.entity.EduSubject;
import com.ghstk.eduservice.entity.excel.SubjectData;
import com.ghstk.eduservice.mapper.EduSubjectMapper;
import com.ghstk.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ghstk.servicebase.exceptionhandler.MyException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author ghStk
 * @since 2021-03-31
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void saveSubject(MultipartFile file) {
        try {
            InputStream in = file.getInputStream();
            EasyExcel.read(in, SubjectData.class, new SubjectExcelListener(this)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
            throw new MyException(20002, "添加课程失败");
        }

    }
}
