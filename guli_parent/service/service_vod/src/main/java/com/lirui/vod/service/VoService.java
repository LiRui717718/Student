package com.lirui.vod.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author ：xxx
 * @description：TODO
 * @date ：2023/5/23 21:01
 */

public interface VoService {
    String uploadVideoAly(MultipartFile file);

    void removeMoreAlyVideo(List videoIdList);
}
