package com.lirui.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.lirui.commonutils.R;
import com.lirui.servicebase.exceptionhandler.GuliException;
import com.lirui.vod.service.VoService;
import com.lirui.vod.utils.InitVodClient;
import com.lirui.vod.utils.VodUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author ：xxx
 * @description：TODO
 * @date ：2023/5/23 20:59
 */
@RestController
@RequestMapping("/eduvod/video")
@CrossOrigin
public class VodController {

    @Autowired
    VoService voService;
    //上传视频到阿里云
    @PostMapping("uploadVideoAly")
    public R uploadVideoAly(MultipartFile file){
        String videoId = voService.uploadVideoAly(file);
        return R.ok().data("videoId",videoId);
    }

    //删除多个视频
    @DeleteMapping("delete-batch")
    public R deleteBatch(@RequestParam("videoIdList")List<String> videoIdList){
        voService.removeMoreAlyVideo(videoIdList);
        return R.ok();
    }

    //根据视频id删除阿里云视频
    @DeleteMapping("removeAlyVideo/{id}")
    public R removeAlyVideo(@PathVariable String id){
        try {
            //初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient(VodUtils.ACCESS_KEY_ID, VodUtils.ACCESS_KEY_SECRET);
            //获取Request和Response对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            //向request设置视频id
            request.setVideoIds(id);
            //调用初始化对象的方法实现
            client.getAcsResponse(request);
        } catch (Exception e) {
            throw new GuliException(2001,"删除视频失败");
        }
        return R.ok();
    }

    //根据视频id获取视频凭证
    @GetMapping("getPlayAuth/{id}")
    public R getPlayAuth(@PathVariable String id ){
        try {
            //创建初始化对象
            DefaultAcsClient defaultAcsClient = InitVodClient.initVodClient(VodUtils.ACCESS_KEY_ID, VodUtils.ACCESS_KEY_SECRET);

            //获取request对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();

            //想request设置视频id
            request.setVideoId(id);

            //调用方法得到凭证
            GetVideoPlayAuthResponse response = defaultAcsClient.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            return R.ok().data("playAuth",playAuth);

        }catch (Exception e){
            throw new GuliException(2001,"锁了");
        }
    }
}
