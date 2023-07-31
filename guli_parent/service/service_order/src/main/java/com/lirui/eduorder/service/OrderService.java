package com.lirui.eduorder.service;

import com.lirui.eduorder.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author testjava
 * @since 2023-06-01
 */
public interface OrderService extends IService<Order> {

    String createOrders(String courseId, String memberIdByJwtToken);
}
