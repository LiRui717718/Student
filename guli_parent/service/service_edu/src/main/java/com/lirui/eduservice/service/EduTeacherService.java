package com.lirui.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lirui.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author testjava
 * @since 2023-05-04
 */
public interface EduTeacherService extends IService<EduTeacher> {


    Map<String, Object> getTeacherFrontList(Page<EduTeacher> pageTeacher);

}
