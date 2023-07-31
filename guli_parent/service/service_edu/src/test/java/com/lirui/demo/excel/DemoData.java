package com.lirui.demo.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author ：xxx
 * @description：TODO
 * @date ：2023/5/16 18:53
 */
@Data
public class DemoData {
    //设置Excel的表头
    @ExcelProperty(value = "学生no" , index = 0)
    private Integer sno;
    @ExcelProperty(value = "学生姓名" , index = 1)
    private String name;
}
