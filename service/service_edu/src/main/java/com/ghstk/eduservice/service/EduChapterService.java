package com.ghstk.eduservice.service;

import com.ghstk.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ghstk.eduservice.entity.vo.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author ghStk
 * @since 2021-04-02
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterList(String courseId);

    boolean  deleteChapter(String chapterId);

    boolean removeByCourseId(String courseId);
}
