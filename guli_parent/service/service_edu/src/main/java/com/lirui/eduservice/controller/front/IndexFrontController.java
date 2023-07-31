package com.lirui.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lirui.commonutils.R;
import com.lirui.eduservice.entity.EduCourse;
import com.lirui.eduservice.entity.EduTeacher;
import com.lirui.eduservice.service.EduCourseService;
import com.lirui.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ：xxx
 * @description：TODO
 * @date ：2023/5/25 11:09
 */
@RestController
@RequestMapping("/eduservice/indexfront")
@CrossOrigin
public class IndexFrontController {

    @Autowired
    EduCourseService courseService;

    @Autowired
    EduTeacherService teacherService;

    //查询前8条热门课程，查询前4条名师
    @GetMapping("index")
    public R index(){
        QueryWrapper<EduCourse> qwCourse = new QueryWrapper<>();
        qwCourse.orderByDesc("id").last("limit 8");
        List<EduCourse> courseList = courseService.list(qwCourse);

        QueryWrapper<EduTeacher> qwTeacher = new QueryWrapper<>();
        qwTeacher.orderByDesc("id").last("limit 4");
        List<EduTeacher> teacherList = teacherService.list(qwTeacher);
        return R.ok().data("eduList",courseList).data("teacherList",teacherList);
    }
}

