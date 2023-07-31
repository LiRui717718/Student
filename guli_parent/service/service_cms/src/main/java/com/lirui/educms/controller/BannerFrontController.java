package com.lirui.educms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lirui.commonutils.R;
import com.lirui.educms.entity.CrmBanner;
import com.lirui.educms.service.CrmBannerService;
import com.lirui.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 首页banner表 前台控制器
 * </p>
 *
 * @author testjava
 * @since 2023-05-25
 */
@RestController
@RequestMapping("/educms/bannerfront")
@CrossOrigin
public class BannerFrontController {

    @Autowired
    private CrmBannerService bannerService;

    //查询所有的轮播图
    @GetMapping("getAllBanner")
    public R getAllBanner(){
        List<CrmBanner> list = bannerService.selectAllBanner();
        return R.ok().data("list",list);
    }
}

