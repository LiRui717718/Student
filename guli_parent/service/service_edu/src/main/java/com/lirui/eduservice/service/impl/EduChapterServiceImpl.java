package com.lirui.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lirui.eduservice.entity.EduChapter;
import com.lirui.eduservice.entity.EduVideo;
import com.lirui.eduservice.entity.chapter.ChapterVo;
import com.lirui.eduservice.entity.chapter.VideoVo;
import com.lirui.eduservice.mapper.EduChapterMapper;
import com.lirui.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lirui.eduservice.service.EduVideoService;
import com.lirui.servicebase.exceptionhandler.GuliException;
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
 * @author testjava
 * @since 2023-05-18
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    EduVideoService videoService;

    @Override
    public List<ChapterVo> getChapterVideo(String courseId) {

        //1 根据课程id查询课程里面所有的章节
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id",courseId);
        List<EduChapter> eduChapterList = baseMapper.selectList(wrapperChapter);

        //2 根据课程id查询课程里面所有的小节
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id",courseId);
        List<EduVideo> eduVideoList = videoService.list(wrapperVideo);

        //创建list集合，用于最终封装数据
        List<ChapterVo> finalList = new ArrayList<>();

        //3 遍历查询章节list集合进行封装
        //遍历查询章节list集合
        for (int i = 0; i < eduChapterList.size(); i++) {
            //每个章节
            EduChapter eduChapter = eduChapterList.get(i);
            //eduChapter对象值复制到ChapterVo里面
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter,chapterVo);
            //把chapterVo放到最终list集合
            finalList.add(chapterVo);

            //创建集合，用于封装章节的小节
            List<VideoVo> videoList = new ArrayList<>();

            //4 遍历查询小节list集合，进行封装
            for (int m = 0; m < eduVideoList.size(); m++) {
                //得到每个小节
                EduVideo eduVideo = eduVideoList.get(m);
                //判断：小节里面chapterid和章节里面id是否一样
                if(eduVideo.getChapterId().equals(eduChapter.getId())) {
                    //进行封装
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo,videoVo);
                    //放到小节封装集合
                    videoList.add(videoVo);
                }
            }
            //把封装之后小节list集合，放到章节对象里面
            chapterVo.setChildren(videoList);
        }
        return finalList;
    }

    //删除章节，如果所章节有小节就不删除
    @Override
    public boolean deleteChapter(String chapterId) {

        //查询所章节下是否有小节
        QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("chapter_id",chapterId);
        int count = videoService.count(queryWrapper);
        if (count > 0) {
            throw new GuliException(2001,"不可删除此章节");
        }else {
            int i = baseMapper.deleteById(chapterId);
            return i>0;
        }


    }
    //删除课程大纲
    @Override
    public void removeChapter(String courseId) {
        QueryWrapper<EduChapter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id",courseId);
        baseMapper.delete(queryWrapper);
    }
}
