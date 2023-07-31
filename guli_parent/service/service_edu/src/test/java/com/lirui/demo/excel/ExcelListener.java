package com.lirui.demo.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

/**
 * @author ：xxx
 * @description：TODO
 * @date ：2023/5/16 19:37
 */

public class ExcelListener extends AnalysisEventListener<DemoData>{

    //读取每一行的数据
    @Override
    public void invoke(DemoData demoData, AnalysisContext analysisContext) {
        System.out.println("###" + demoData);
    }

    //读取表头的数据
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头" + headMap);
    }

    //读取每一行之后的操作
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }


}
