package com.lirui.eduservice.client;

import com.lirui.commonutils.R;
import com.lirui.eduservice.client.impl.VodClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author ：xxx
 * @description：调用其他模块服务的接口
 * @date ：2023/5/24 15:34
 */
@Component
@FeignClient(name ="service-vod",fallback = VodClientImpl.class)
public interface VodClient {

    //定义调用方法的路径
    @DeleteMapping("/eduvod/video/removeAlyVideo/{id}")
    public R removeAlyVideo(@PathVariable("id") String id);

    //删除多个视频
    @DeleteMapping("/eduvod/video/delete-batch")
    public R deleteBatch(@RequestParam("videoIdList") List<String> videoIdList);

}
