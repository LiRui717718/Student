package com.lirui.eduservice.controller;


import com.lirui.commonutils.R;
import com.lirui.eduservice.client.VodClient;
import com.lirui.eduservice.entity.EduVideo;
import com.lirui.eduservice.service.EduVideoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-03-02
 */
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService videoService;

    @Autowired
    private VodClient vodClient;

    //添加小节
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo) {
        videoService.save(eduVideo);
        return R.ok();
    }

    //删除小节
    // TODO 后面这个方法需要完善：删除小节时候，同时把里面视频删除
    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable String id) {
        EduVideo byId = videoService.getById(id);
        String videoSourceId = byId.getVideoSourceId();
        if (!StringUtils.isEmpty(videoSourceId)) {

            vodClient.removeAlyVideo(videoSourceId);

        }
        videoService.removeById(id);
        return R.ok();
    }

    //修改小节 TODO
    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo video){

        return R.ok();
    }

}

