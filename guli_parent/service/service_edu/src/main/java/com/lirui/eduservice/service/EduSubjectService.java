package com.lirui.eduservice.service;

import com.lirui.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lirui.eduservice.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author testjava
 * @since 2023-05-16
 */
public interface EduSubjectService extends IService<EduSubject> {

    void saveSubject(MultipartFile file , EduSubjectService eduSubjectService);

    List<OneSubject> getAllSubject();
}
