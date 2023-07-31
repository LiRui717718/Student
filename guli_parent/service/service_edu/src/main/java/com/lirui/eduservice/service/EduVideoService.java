package com.lirui.eduservice.service;

import com.lirui.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author testjava
 * @since 2023-05-18
 */
public interface EduVideoService extends IService<EduVideo> {

    void removeVideo(String courseId);
}
