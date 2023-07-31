package com.lirui.oss.controller;

import com.lirui.commonutils.R;
import com.lirui.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author ：xxx
 * @description：TODO
 * @date ：2023/5/16 14:18
 */

@RestController
@CrossOrigin
@RequestMapping("/eduoss/fileoss")
public class OssController {
    @Autowired
    private OssService ossService;

    @PostMapping
    public R uploadFileAvatar(MultipartFile file){
        String url = ossService.uploadFileAvatar(file);
        return R.ok().data("url",url);
    }

}
