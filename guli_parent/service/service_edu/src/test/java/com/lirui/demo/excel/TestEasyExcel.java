package com.lirui.demo.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：xxx
 * @description：TODO
 * @date ：2023/5/16 19:01
 */

public class TestEasyExcel {
    public static void main(String[] args) {
       /* //实现Excel写的操作
        String filename = "D:\\01.xlsx";
        EasyExcel.write(filename,DemoData.class).sheet("学生列表").doWrite(get());*/
        //实现读的操作
        String filename = "D:\\01.xlsx";
        EasyExcel.read(filename,DemoData.class,new ExcelListener()).sheet().doRead();
    }
    private static List<DemoData> get(){
        List<DemoData> l = new ArrayList<>();
        for (int i = 0; i<10 ; i++){
            DemoData demoData = new DemoData();
            demoData.setSno(i);
            demoData.setName("李瑞"+ i);
            l.add(demoData);
        }
        return l;
    }


}
