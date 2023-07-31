package com.lirui.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.lirui.servicebase.exceptionhandler.GuliException;
import com.lirui.vod.service.VoService;
import com.lirui.vod.utils.InitVodClient;
import com.lirui.vod.utils.VodUtils;
import org.apache.poi.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * @author ：xxx
 * @description：TODO
 * @date ：2023/5/23 21:01
 */
@Service
public class VoServiceImpl implements VoService {
    @Override
    public String uploadVideoAly(MultipartFile file) {
        try {
            //accessKeyId, accessKeySecret
            //fileName：上传文件原始名称
            // 01.03.09.mp4
            String fileName = file.getOriginalFilename();
            //title：上传之后显示名称
            String title = fileName.substring(0, fileName.lastIndexOf("."));//进行截取文件名，不要点后的名字
            //inputStream：上传文件输入流
            InputStream inputStream = file.getInputStream();
            UploadStreamRequest request = new UploadStreamRequest(VodUtils.ACCESS_KEY_ID, VodUtils.ACCESS_KEY_SECRET, title, fileName, inputStream);

            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);

            String videoId = null;
            if (response.isSuccess()) {
                videoId = response.getVideoId();
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                videoId = response.getVideoId();
            }
            return videoId;
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void removeMoreAlyVideo(List videoIdList) {
        try {
            //初始化
            DefaultAcsClient client = InitVodClient.initVodClient(VodUtils.ACCESS_KEY_ID, VodUtils.ACCESS_KEY_SECRET);
            //获取request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            //将集合转换一下形式
            String videoIds = StringUtil.join(videoIdList.toArray(), ",");
            //传入id值
            request.setVideoIds(videoIds);
            //调用初始化对象的方法实现
            client.getAcsResponse(request);
        } catch (ClientException e) {
            throw new GuliException(2001,"删除视频失败");
        }


    }
}
