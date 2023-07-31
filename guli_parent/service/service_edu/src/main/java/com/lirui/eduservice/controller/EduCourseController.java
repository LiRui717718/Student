package com.lirui.eduservice.controller;


import com.lirui.commonutils.R;
import com.lirui.eduservice.entity.EduChapter;
import com.lirui.eduservice.entity.EduCourse;
import com.lirui.eduservice.entity.vo.CourseInfoVo;
import com.lirui.eduservice.entity.vo.CoursePublishVo;
import com.lirui.eduservice.service.EduChapterService;
import com.lirui.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2023-05-18
 */
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {
    @Autowired
    EduCourseService eduCourseService;

    @Autowired
    EduChapterService eduChapterService;

    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        String id = eduCourseService.addCourseInfo(courseInfoVo);
        return R.ok().data("courseId",id);
    }

    //根据课程id查询信息表和描述表
    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId){
        CourseInfoVo courseInfoVo = eduCourseService.getCourseInfo(courseId);
        return R.ok().data("courseInfoVo",courseInfoVo);
    }

    //根据课程id查询课程确认信息
    @GetMapping("getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable String id) {
        CoursePublishVo coursePublishVo = eduCourseService.publishCourseInfo(id);
        return R.ok().data("publishCourse",coursePublishVo);
    }

    //课程最终发布
    //修改课程状态
    @PostMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable String id){
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");
        eduCourseService.updateById(eduCourse);
        return R.ok();
    }

    //课程列表 基本实现
    //TODO  完善条件查询带分页
    @GetMapping
    public R getCourseList() {
        List<EduCourse> list = eduCourseService.list(null);
        return R.ok().data("list",list);
    }

    //删除课程
    @DeleteMapping("{courseId}")
    public R deleteCourse(@PathVariable String courseId){
        eduCourseService.removeCourse(courseId);
        return R.ok();
    }

}

