package com.lirui.eduservice.controller;


import com.lirui.commonutils.MD5;
import com.lirui.commonutils.R;
import com.lirui.eduservice.entity.subject.OneSubject;
import com.lirui.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2023-05-16
 */
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService eduSubjectService;

    //添加课程分类
    //获取上传过来的文件，把文件内容读取出来
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file){

        eduSubjectService.saveSubject(file,eduSubjectService);
        return R.ok();
    }

    //课程分类的列表（树形结构）
    @GetMapping("getAllSubject")
    public R getAllSubject(){
        List<OneSubject> list = eduSubjectService.getAllSubject();
        return R.ok().data("list",list);
    }



}

