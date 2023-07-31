package com.lirui.eduorder.service;

import com.lirui.eduorder.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author testjava
 * @since 2023-06-01
 */
public interface PayLogService extends IService<PayLog> {

    Map createNative(String orderNo) throws Exception;
}
