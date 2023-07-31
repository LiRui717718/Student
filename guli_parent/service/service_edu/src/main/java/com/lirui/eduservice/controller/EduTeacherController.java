package com.lirui.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lirui.commonutils.R;
import com.lirui.eduservice.entity.EduTeacher;
import com.lirui.eduservice.entity.vo.TeacherQuery;
import com.lirui.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2023-05-04
 */
@Api(tags = "讲师管理接口")
@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin//跨域请求的注解
public class EduTeacherController {

    //注入service
    @Autowired
    EduTeacherService eduTeacherService;
    @GetMapping("findAll")
    @ApiOperation("讲师查询")
    public R findAllTeacher(){
        //调用service的方法实现返回查询所用数据
        List<EduTeacher> list = eduTeacherService.list(null);
        return R.ok().data("items",list);
    }

    //逻辑删除讲师的方法
    @DeleteMapping("{id}")
    @ApiOperation("逻辑删除讲师")
    public R removeTeacher(@ApiParam(name = "id" , value = "讲师ID" , required = true) @PathVariable String id){
        boolean b = eduTeacherService.removeById(id);
        if (b) {
            return R.ok();
        }else {
            return R.error();
        }
    }

    //3 分页查询讲师的方法
    //current 当前页
    //limit 每页记录数
    @GetMapping("pageTeacher/{current}/{limit}")
    @ApiOperation("讲师分页查询")
    public R pageListTeacher(
            @PathVariable long current,
            @PathVariable long limit
    ) {
        //创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(current,limit);
        //调用方法实现分页
        //调用方法时候，底层封装，把分页所有数据封装到pageTeacher对象里面
        eduTeacherService.page(pageTeacher,null);

        long total = pageTeacher.getTotal();//总记录数
        List<EduTeacher> records = pageTeacher.getRecords(); //数据list集合

//        Map map = new HashMap();
//        map.put("total",total);
//        map.put("rows",records);
//        return R.ok().data(map);

        return R.ok().data("total",total).data("rows",records);
    }

    //条件查询带分页的方法
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    @ApiOperation("讲师条件分页查询")
    public R pageTeacherCondition(
            @PathVariable long current,
            @PathVariable long limit,
            @RequestBody(required = false) TeacherQuery teacherQuery
    ){
        Page<EduTeacher> pageTeacher = new Page<>(current,limit);
        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();

        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        Integer level = teacherQuery.getLevel();
        String name = teacherQuery.getName();
        if (!StringUtils.isEmpty(name)){
            queryWrapper.like("name",name);
        }if (!StringUtils.isEmpty(level)){
            queryWrapper.eq("level",level);
        }if (!StringUtils.isEmpty(end)){
            queryWrapper.le("gmt_create",end);
        }if (!StringUtils.isEmpty(begin)){
            queryWrapper.ge("gmt_create",begin);
        }
        //排序根据时间排序
        queryWrapper.orderByDesc("gmt_create");
        eduTeacherService.page(pageTeacher,queryWrapper);

        long total = pageTeacher.getTotal();//总记录数
        List<EduTeacher> records = pageTeacher.getRecords(); //数据list集合

//        Map map = new HashMap();
//        map.put("total",total);
//        map.put("rows",records);
//        return R.ok().data(map);

        return R.ok().data("total",total).data("rows",records);
    }

    //添加讲师接口方法
    @PostMapping("addTeacher")
    @ApiOperation("添加讲师")
    public R addTeacher(@RequestBody EduTeacher eduTeacher){
        boolean save = eduTeacherService.save(eduTeacher);
        if (save) {
            return R.ok();
        }else {
            return R.error();
        }
    }

    //根据讲师ID进行查询
    @GetMapping("getTeacher/{id}")
    @ApiOperation("根据讲师ID进行查询,为了数据的回显")
    public R getTeacher(@PathVariable String id){
        EduTeacher eduTeacher = eduTeacherService.getById(id);
        return R.ok().data("teacher",eduTeacher);
    }

    //讲师修改功能
    @PostMapping("updateTeacher")
    @ApiOperation("修改讲师")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher){
        boolean b = eduTeacherService.updateById(eduTeacher);
        if (b) {
            return R.ok();
        }else {
            return R.error();
        }
    }

}
   
