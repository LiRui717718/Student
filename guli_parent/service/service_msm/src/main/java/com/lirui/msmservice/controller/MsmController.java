package com.lirui.msmservice.controller;

import com.lirui.commonutils.R;
import com.lirui.msmservice.service.MsmService;
import com.lirui.msmservice.util.RandomUtil;
import com.lirui.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author ：xxx
 * @description：TODO
 * @date ：2023/5/28 17:39
 */
@RestController
@RequestMapping("/edumsm/msm")
@CrossOrigin
public class MsmController {

    @Autowired
    MsmService msmService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    //发送短信
    @GetMapping("send/{phone}")
    public R sendMsm(@PathVariable String phone){
        //查找redis
        String code = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(code)) {
            return R.ok();
        }
        //获取碎甲验证码
        code = RandomUtil.getFourBitRandom();
        //将验证码放到Map集合中
        Map<String, Object> param = new HashMap<>();
        param.put("code",code);
        //将验证码放到redis
        boolean isSend = msmService.send(phone,param);
        if (isSend) {
            //获取随机验证码放到redis中
            redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);
            return R.ok();
        }
        return R.error().message("短信发送失败");
    }
}
