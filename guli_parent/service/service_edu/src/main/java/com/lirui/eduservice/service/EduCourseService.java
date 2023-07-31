package com.lirui.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lirui.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lirui.eduservice.entity.frontvo.CourseFrontVo;
import com.lirui.eduservice.entity.frontvo.CourseWebVo;
import com.lirui.eduservice.entity.vo.CourseInfoVo;
import com.lirui.eduservice.entity.vo.CoursePublishVo;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2023-05-18
 */
public interface EduCourseService extends IService<EduCourse> {

    String addCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfo(String courseId);

    void UpdateCourseInfo(CourseInfoVo courseInfoVo);

    //根据课程id查询课程确认信息
    CoursePublishVo publishCourseInfo(String id);

    void removeCourse(String courseId);

    Map<String, Object> getFrontCourseList(Page<EduCourse> coursePage, CourseFrontVo courseFrontVo);

    CourseWebVo getBaseCourseInfo(String courseId);
}
