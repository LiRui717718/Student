package com.lirui.eduservice.service;

import com.lirui.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lirui.eduservice.entity.chapter.ChapterVo;
import com.lirui.eduservice.entity.vo.CoursePublishVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2023-05-18
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterVideo(String courseId);

    boolean deleteChapter(String chapterId);

    void removeChapter(String courseId);

}
