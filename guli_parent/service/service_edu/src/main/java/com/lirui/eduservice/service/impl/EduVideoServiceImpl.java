package com.lirui.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lirui.eduservice.client.VodClient;
import com.lirui.eduservice.entity.EduVideo;
import com.lirui.eduservice.mapper.EduVideoMapper;
import com.lirui.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2023-05-18
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    VodClient vodClient;

    //删除小节
    //todo 在删除小节的时候也需要删除视频文件
    @Override
    public void removeVideo(String courseId) {
        //删除多个视频
        QueryWrapper<EduVideo> qw = new QueryWrapper<>();
        qw.eq("course_id",courseId).select("video_source_id");
        List<EduVideo> eduVideos = baseMapper.selectList(qw);

        List<String> videoIds = new ArrayList<>();//这里需要转换为String泛型的List集合为了后期根据逗号分割
        for (int i = 0; i < eduVideos.size(); i++) {
            EduVideo eduVideo = eduVideos.get(i);
            String videoSourceId = eduVideo.getVideoSourceId();
            if (!StringUtils.isEmpty(videoIds)) {
                videoIds.add(videoSourceId);
            }
        }
        if (videoIds.size() > 0) {
            vodClient.deleteBatch(videoIds);
        }
        //删除小节
        QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id",courseId);
        baseMapper.delete(queryWrapper);
    }
}
