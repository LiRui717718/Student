package com.lirui.servicebase.exceptionhandler;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ：xxx
 * @description：TODO
 * @date ：2023/5/6 16:26
 */
@Data
@AllArgsConstructor//有参构造
@NoArgsConstructor//无参构造
public class GuliException extends RuntimeException {
    private Integer code;//状态码
    private String msg;//错误信息
}
