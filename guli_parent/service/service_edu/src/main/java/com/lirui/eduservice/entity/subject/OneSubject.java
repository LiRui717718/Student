package com.lirui.eduservice.entity.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：xxx
 * @description：一级分类实体
 * @date ：2023/5/17 14:46
 */
@Data
public class OneSubject {

    private String id;
    private String title;

    private List<TwoSubject> children = new ArrayList<>();
}
