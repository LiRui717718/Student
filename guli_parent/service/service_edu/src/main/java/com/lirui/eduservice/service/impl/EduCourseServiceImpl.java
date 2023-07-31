package com.lirui.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lirui.eduservice.entity.EduCourse;
import com.lirui.eduservice.entity.EduCourseDescription;
import com.lirui.eduservice.entity.EduSubject;
import com.lirui.eduservice.entity.EduTeacher;
import com.lirui.eduservice.entity.frontvo.CourseFrontVo;
import com.lirui.eduservice.entity.frontvo.CourseWebVo;
import com.lirui.eduservice.entity.vo.CourseInfoVo;
import com.lirui.eduservice.entity.vo.CoursePublishVo;
import com.lirui.eduservice.mapper.EduCourseMapper;
import com.lirui.eduservice.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lirui.servicebase.exceptionhandler.GuliException;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2023-05-18
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    EduCourseDescriptionService eduCourseDescriptionService;

    @Autowired
    EduCourseService eduCourseService;

    @Autowired
    EduSubjectService eduSubjectService;

    @Autowired
    EduVideoService eduVideoService;

    @Autowired
    EduChapterService eduChapterService;

    @Override
    public String addCourseInfo(CourseInfoVo courseInfoVo) {

        //将获取的课程基本表添加到数据库中
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if (insert == 0) {
            //添加失败
            throw new GuliException(2001,"添加课程信息失败");

        }
        //获取添加之后课程id
        String cid = eduCourse.getId();
        //将介绍表也添加到数据库中
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        //这两个表是有关系的，需要把两个id进行手动设置同样的
        //设置描述id就是课程id
        eduCourseDescription.setId(cid);
        eduCourseDescriptionService.save(eduCourseDescription);
        return eduCourse.getId();
    }

    @Override
    public CourseInfoVo getCourseInfo(String courseId) {

        //查询课程基本表
        EduCourse eduCourse = baseMapper.selectById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse,courseInfoVo);

        //查询课程描述表
        EduCourseDescription byId = eduCourseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(byId.getDescription());
        return courseInfoVo;
    }

    //
    @Override
    public void UpdateCourseInfo(CourseInfoVo courseInfoVo) {

        //修改基本信息表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int i = baseMapper.updateById(eduCourse);
        if (i == 0) {
            throw new GuliException(2001,"修改信息表失败");
        }

        //修改描述表
        EduCourseDescription description = new EduCourseDescription();
        description.setId(courseInfoVo.getId());
        description.setDescription(courseInfoVo.getDescription());
        eduCourseDescriptionService.updateById(description);
    }
    //根据课程id查询课程确认信息
    @Override
    public CoursePublishVo publishCourseInfo(String id) {
        //调用mapper
        CoursePublishVo publishCourseInfo = baseMapper.getPublishCourseInfo(id);
        return publishCourseInfo;
    }

    //根据课程Id删除课程
    @Override
    public void removeCourse(String courseId) {

        //删除课程中的小节
        eduVideoService.removeVideo(courseId);

        //删除课程中的大纲
        eduChapterService.removeChapter(courseId);

        //删除课程描述
        eduCourseDescriptionService.removeById(courseId);

        //

        //删除课程本身
        int i = baseMapper.deleteById(courseId);
        if (i == 0) {
            throw new GuliException(20001,"删除失败");
        }
    }

    @Override
    public Map<String, Object> getFrontCourseList(Page<EduCourse> pageParam, CourseFrontVo courseFrontVo) {
        QueryWrapper<EduCourse> qw = new QueryWrapper<>();


        if (!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())) {
            qw.eq("subjectParentId",courseFrontVo.getSubjectParentId());
        }
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectId())){
            qw.eq("subjectParentId",courseFrontVo.getSubjectId());
        }
        if (!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())){
            qw.orderByDesc("buyCountSort",courseFrontVo.getBuyCountSort());
        }
        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())){
            qw.orderByDesc("buyCountSort",courseFrontVo.getGmtCreateSort());
        }
        if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())){
            qw.orderByDesc("buyCountSort",courseFrontVo.getPriceSort());
        }
        baseMapper.selectPage(pageParam,qw);

        List<EduCourse> records = pageParam.getRecords();//显示课程列表
        long current = pageParam.getCurrent();//当前所在的页数
        long pages = pageParam.getPages();//总页数
        long size = pageParam.getSize();//每页要查询的记录数，表示每页上显示的课程数量
        long total = pageParam.getTotal();//符合当前条件的记录数
        boolean hasNext = pageParam.hasNext();//下一页
        boolean hasPrevious = pageParam.hasPrevious();//上一页

        //把分页数据获取出来，放到map集合
        Map<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }

    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
        return baseMapper.getBaseCourseInfo(courseId);
    }
}
