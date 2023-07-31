package com.lirui.eduservice.client.impl;

import com.lirui.commonutils.R;
import com.lirui.eduservice.client.VodClient;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ：xxx
 * @description：当调用的服务宕机了，消费着调用不到生产者就会执行这个类（熔断降级操作）
 * @date ：2023/5/24 18:43
 */
@Component
public class VodClientImpl implements VodClient {
    @Override
    public R removeAlyVideo(String id) {

        return R.error().message("单个视频删除出错");
    }

    @Override
    public R deleteBatch(List<String> videoIdList) {

        return R.error().message("多 个视频删除出错");
    }
}
