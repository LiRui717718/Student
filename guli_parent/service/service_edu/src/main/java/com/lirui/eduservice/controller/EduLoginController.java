package com.lirui.eduservice.controller;

import com.lirui.commonutils.R;
import org.springframework.web.bind.annotation.*;

/**
 * @author ：xxx
 * @description：TODO
 * @date ：2023/5/10 16:48
 */
@CrossOrigin//解决跨域问题
@RestController
@RequestMapping("/eduservice/user")
public class EduLoginController {

    @PostMapping("login")
    public R login(){
        return R.ok().data("tocken","admin");
    }

    @GetMapping("info")
    public R info(){
        return R.ok().data("roles","[lirui]").data("name","lirui").data("avatar",null);
    }
}
