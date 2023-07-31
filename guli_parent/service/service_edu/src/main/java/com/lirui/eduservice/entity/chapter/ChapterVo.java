package com.lirui.eduservice.entity.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：xxx
 * @description：TODO
 * @date ：2023/5/22 8:14
 */
@Data
public class ChapterVo {

    private String id;

    private String title;

    private List<VideoVo> children = new ArrayList<>();
}
