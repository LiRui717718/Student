package com.lirui.eduservice.entity.chapter;

import lombok.Data;
import springfox.documentation.service.Tags;

/**
 * @author ：xxx
 * @description：TODO
 * @date ：2023/5/22 8:15
 */
@Data
public class VideoVo {

    private String id;

    private String title;

    private String videoSourceId;//视频id
}
