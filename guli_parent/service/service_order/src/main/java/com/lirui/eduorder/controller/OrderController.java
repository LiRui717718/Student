package com.lirui.eduorder.controller;


import com.lirui.commonutils.JwtUtils;
import com.lirui.commonutils.R;
import com.lirui.eduorder.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2023-06-01
 */
@RestController
@RequestMapping("/eduorder/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    //生成订单方法
    @PostMapping("createOrder/{courseId}")
    public R saveOrder(@PathVariable String courseId, HttpServletRequest request){
        //创建订单，返回订单号
        String orderNo = orderService.createOrders(courseId, JwtUtils.getMemberIdByJwtToken(request));
        return R.ok().data("orderId",orderNo);
    }
}

