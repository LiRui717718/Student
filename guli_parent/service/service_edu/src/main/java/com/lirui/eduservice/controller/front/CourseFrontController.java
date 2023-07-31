package com.lirui.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lirui.commonutils.CourseWebVoOrder;
import com.lirui.commonutils.R;
import com.lirui.eduservice.entity.EduCourse;
import com.lirui.eduservice.entity.EduTeacher;
import com.lirui.eduservice.entity.chapter.ChapterVo;
import com.lirui.eduservice.entity.frontvo.CourseFrontVo;
import com.lirui.eduservice.entity.frontvo.CourseWebVo;
import com.lirui.eduservice.service.EduChapterService;
import com.lirui.eduservice.service.EduCourseService;
import com.lirui.eduservice.service.EduTeacherService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author ：xxx
 * @description：TODO
 * @date ：2023/5/31 10:23
 */
@RestController
@RequestMapping("/eduservice/coursefront")
@CrossOrigin
public class CourseFrontController {

    @Autowired
    EduCourseService courseService;

    @Autowired
    EduChapterService chapterService;

    //条件查询带分页查询课程
    @PostMapping("getFrontCourseList/{page}/{limit}")
    public R getFrontCourseList(@PathVariable long page , @PathVariable long limit, @RequestBody(required = false) CourseFrontVo courseFrontVo){
        Page<EduCourse> coursePage = new Page<>(page, limit);
        Map <String,Object> map = courseService.getFrontCourseList(coursePage,courseFrontVo);
        return R.ok().data(map);
    }

    @GetMapping("getFrontCourseInfo/{courseId}")
    public R getFrontCourseInfo(@PathVariable String courseId){
        //根据课程id编写sql语句查询课程信息
        CourseWebVo courseWebVo = courseService.getBaseCourseInfo(courseId);

        //根据课程id查询章节和小节
        List<ChapterVo> chapterVideoList = chapterService.getChapterVideo(courseId);

        return R.ok().data("courseWebVo",courseWebVo).data("chapterVideoList",chapterVideoList);
    }

    //根据课程id查询课程信息
    @PostMapping("getCourseInfoOrder/{id}")
    public CourseWebVoOrder getCourseInfoOrder(@PathVariable String id){
        CourseWebVo baseCourseInfo = courseService.getBaseCourseInfo(id);
        CourseWebVoOrder courseWebVoOrder = new CourseWebVoOrder();
        BeanUtils.copyProperties(baseCourseInfo,courseWebVoOrder);
        return courseWebVoOrder;
    }
}
