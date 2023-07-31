package com.lirui.eduservice.controller;


import com.lirui.commonutils.R;
import com.lirui.eduservice.entity.EduChapter;
import com.lirui.eduservice.entity.chapter.ChapterVo;
import com.lirui.eduservice.entity.vo.CourseInfoVo;
import com.lirui.eduservice.entity.vo.CoursePublishVo;
import com.lirui.eduservice.service.EduChapterService;
import com.lirui.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
@RequestMapping("/eduservice/chapter")
@CrossOrigin
public class EduChapterController {

    @Autowired
    EduChapterService eduChapterService;

    @Autowired
    EduCourseService eduCourseService;

    @GetMapping("getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable String courseId){

        List<ChapterVo> list =eduChapterService.getChapterVideo(courseId);
        return R.ok().data("allChapterVideo",list);
    }

    //修改课程信息
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        eduCourseService.UpdateCourseInfo(courseInfoVo);
        return R.ok();
    }

    //添加章节
    @PostMapping("addChapter")
    public R addChapter(@RequestBody EduChapter chapter){
        eduChapterService.save(chapter);
        return R.ok();
    }

    //修改章节
    @PostMapping("updateChapter")
    public R updateChapter(@RequestBody EduChapter chapter){
        eduChapterService.updateById(chapter);
        return R.ok();
    }

    //通过id查询章节
    @GetMapping("getChapterInfo/{chapterId}")
    public R getChapterInfo(@PathVariable String chapterId){
        eduChapterService.getById(chapterId);
        return R.ok();
    }

    //删除章节
    @DeleteMapping("{chapterId}")
    public R deleteChapter(@PathVariable String chapterId){
        boolean flag = eduChapterService.deleteChapter(chapterId);
        if (flag == true) {
            return R.ok();
        }else {
            return R.error();
        }

    }

}

