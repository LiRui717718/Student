package com.lirui.eduorder.controller;


import com.lirui.commonutils.R;
import com.lirui.eduorder.entity.PayLog;
import com.lirui.eduorder.service.PayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2023-06-01
 */
@RestController
@RequestMapping("/eduorder/paylog")
public class PayLogController {

    @Autowired
    PayLogService payLogService;

    @GetMapping("createNative/{orderNo}")
    public R createNative(@PathVariable String orderNo){

        Map map = payLogService.createNative(orderNo);

        return R.ok().data(map);
    }

}

