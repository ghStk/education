package com.ghstk.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ghstk.eduservice.client.VodClient;
import com.ghstk.eduservice.entity.EduChapter;
import com.ghstk.eduservice.entity.EduVideo;
import com.ghstk.eduservice.entity.vo.chapter.ChapterVo;
import com.ghstk.eduservice.entity.vo.chapter.VideoVo;
import com.ghstk.eduservice.mapper.EduChapterMapper;
import com.ghstk.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ghstk.eduservice.service.EduVideoService;
import com.ghstk.servicebase.exceptionhandler.MyException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author ghStk
 * @since 2021-04-02
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    EduVideoService videoService;
    @Autowired
    VodClient vodClient;

    @Override
    public List<ChapterVo> getChapterList(String courseId) {
        // 查出大纲chapter
        QueryWrapper<EduChapter> chapterWrapper = new QueryWrapper<>();
        chapterWrapper.eq("course_id", courseId);
        List<EduChapter> chapterList = this.list(chapterWrapper);
        // 查出小节video
        QueryWrapper<EduVideo> videoWrapper = new QueryWrapper<>();
        videoWrapper.eq("course_id", courseId);
        List<EduVideo> videoList = videoService.list(videoWrapper);

        List<ChapterVo> response = new ArrayList<>();
        // 遍历大纲
        for (EduChapter chapter : chapterList) {
            if (chapter.getCourseId().equals(courseId)) {
                ChapterVo chapterVo = new ChapterVo();
                BeanUtils.copyProperties(chapter, chapterVo);
                // 遍历小节
                for (EduVideo video : videoList) {
                    if (video.getCourseId().equals(courseId)
                            && video.getChapterId().equals(chapter.getId())) {
                        VideoVo videoVo = new VideoVo();
                        BeanUtils.copyProperties(video, videoVo);
                        chapterVo.getChildren().add(videoVo);
                    }
                }
                response.add(chapterVo);
            }
        }

        return response;
    }

    /**
     * 删除章节 小节 视频
     */
    @Override
    public boolean deleteChapter(String chapterId) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id", chapterId);
        if (videoService.count(wrapper) > 0) { //删除小节&视频
            ArrayList<String> vodIdList = new ArrayList<>();
            List<EduVideo> videoList = videoService.list(wrapper);
            for (EduVideo v : videoList) {
                if (v.getVideoSourceId() != null) {
                    vodIdList.add(v.getVideoSourceId());
                }
                videoService.removeById(v.getId());
            }
            if (vodIdList.size() > 0) {
                vodClient.deleteVideoBatch(vodIdList);
            }
        }
        return this.removeById(chapterId);
    }

    @Override
    public boolean removeByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        if (baseMapper.delete(wrapper) <= 0) {
//            throw new MyException(20001,"没有找到章节");
        }
        return true;
    }
}
