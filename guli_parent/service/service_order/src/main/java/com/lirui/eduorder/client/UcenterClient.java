package com.lirui.eduorder.client;

import com.lirui.commonutils.UcenterMemberOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author ：xxx
 * @description：TODO
 * @date ：2023/6/1 14:05
 */
@Component
@FeignClient("service-ucenter")
public interface UcenterClient {
    @PostMapping("/educenter/member/getUserInfoOrder/{id}")
    public UcenterMemberOrder getUserInfoOrder(@PathVariable("id") String id);
}
