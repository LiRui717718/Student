package com.lirui.msmservice.service;

import java.util.Map;

/**
 * @author ：xxx
 * @description：TODO
 * @date ：2023/5/28 17:39
 */
public interface MsmService {
    boolean send(String phone, Map<String, Object> param);
}
