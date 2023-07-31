package com.lirui.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author ：xxx
 * @description：TODO
 * @date ：2023/5/16 14:18
 */
public interface OssService {

    //上传头像
    public String uploadFileAvatar(MultipartFile file);
}
