package com.lirui.educenter.controller;


import com.lirui.commonutils.JwtUtils;
import com.lirui.commonutils.R;
import com.lirui.commonutils.UcenterMemberOrder;
import com.lirui.educenter.entity.UcenterMember;
import com.lirui.educenter.entity.vo.RegisterVo;
import com.lirui.educenter.service.UcenterMemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2023-05-29
 */
@RestController
@RequestMapping("/educenter/member")
@CrossOrigin
public class UcenterMemberController {

    @Autowired
    UcenterMemberService memberService;


    //登录
    @PostMapping("login")//对于missing报错是用了@RequestBody这个注解但是没有用post提交而用了get提交
    public R loginUser(@RequestBody UcenterMember member){

        String token = memberService.login(member);
        return R.ok().data("token",token);
    }

    //注册
    @PostMapping("register")
    public R register(@RequestBody RegisterVo registerVo){

        memberService.register(registerVo);
        return R.ok();
    }

    //根据token获取用户信息（解析token）
    @GetMapping("getMemberInfo")
    public R getMemberInfo(HttpServletRequest request){
        //调用JWT中的方法，解析请求头中的token值中的id值，
        String id = JwtUtils.getMemberIdByJwtToken(request );
        //查询数据库，通过id值查询
        UcenterMember member = memberService.getById(id);
        return R.ok().data("userInfo",member);
    }
    //根据用户id查询用户基本信息
    @PostMapping("getUserInfoOrder/{id}")
    public UcenterMemberOrder getUserInfoOrder(@PathVariable String id){
        UcenterMember openIdMember = memberService.getOpenIdMember(id);
        UcenterMemberOrder ucenterMemberOrder = new UcenterMemberOrder();
        BeanUtils.copyProperties(openIdMember,ucenterMemberOrder);
        return ucenterMemberOrder;
    }
}

