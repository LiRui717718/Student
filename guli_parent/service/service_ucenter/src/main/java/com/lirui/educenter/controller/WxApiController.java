package com.lirui.educenter.controller;

import com.google.gson.Gson;
import com.lirui.commonutils.JwtUtils;
import com.lirui.commonutils.UcenterMemberOrder;
import com.lirui.educenter.entity.UcenterMember;
import com.lirui.educenter.service.UcenterMemberService;
import com.lirui.educenter.utils.ConstantWxUtils;
import com.lirui.educenter.utils.HttpClientUtils;
import com.lirui.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * @author ：xxx
 * @description：TODO
 * @date ：2023/5/30 15:17
 */
@CrossOrigin
@Controller
@RequestMapping("/api/ucenter/wx")
public class WxApiController {

    @Autowired
    UcenterMemberService ucenterMemberService;
    //获取扫描人信息，添加数据
    @GetMapping("callback")
    public String callback(String code , String status) {
        try {
            //1 获取code值，临时票据，类似于验证码
            //2 拿着code请求 微信固定的地址，得到两个值 access_token 和 openid
            String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";
            //拼接三个参数 ：id  秘钥 和 code值
            String accessTokenUrl = String.format(
                    baseAccessTokenUrl,
                    ConstantWxUtils.WX_OPEN_APP_ID,
                    ConstantWxUtils.WX_OPEN_APP_SECRET,
                    code
            );
            String accessTokenInfo= HttpClientUtils.get(accessTokenUrl);
            //请求这个拼接好的地址，得到返回两个值access_token和openid
            //手机用httpclient发送请求，得到返回结果
            //使用json转换工具Gson
            Gson gson = new Gson();
            HashMap hashMap = gson.fromJson(accessTokenInfo, HashMap.class);
            String access_token = (String) hashMap.get("access_token");
            String openid = (String) hashMap.get("openid");
            //3 拿着得到access_token 和 openid，再去请求微信提供固定的地址，获取到扫描人信息
            //访问微信的资源服务器，获取用户信息
            String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                    "?access_token=%s" +
                    "&openid=%s";
            //拼接两个参数
            String userInfoUrl = String.format(
                    baseUserInfoUrl,
                    access_token,
                    openid
            );
            String userInfoUrlInfo = HttpClientUtils.get(userInfoUrl);
            HashMap userInfoMap = gson.fromJson(userInfoUrlInfo, HashMap.class);
            String nickname = (String)userInfoMap.get("nickname");//昵称
            String headimgurl = (String)userInfoMap.get("headimgurl");//头像
            //判断数据库是否有这个值
            UcenterMember member = ucenterMemberService.getOpenIdMember(openid);
            if (member == null) {
                UcenterMember ucenterMember = new UcenterMember();
                ucenterMember.setOpenid(openid);
                ucenterMember.setNickname(nickname);
                ucenterMember.setAvatar(headimgurl);
                ucenterMemberService.save(ucenterMember);
            }
            //使用jwt根据member对象生成token字符串
            String jwtToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());
            //最后：返回首页面，通过路径传递token字符串
            return "redirect:http://localhost:3000?token="+jwtToken;
        }catch (Exception e){

            throw new GuliException(2001,"微信登录失败");
        }

    }

    @GetMapping("login")
    public String getWxCode(){

        //微信开发平台授权baseUrl   %s相当于占位符？
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=wxed9954c01bb89b47" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        //将url进行编码
        String wxUrl = ConstantWxUtils.WX_OPEN_REDIRECT_URL;
        try {
            wxUrl = URLEncoder.encode(wxUrl, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }


        //设置%s中的值
        String url = String.format(
                baseUrl,
                //ConstantWxUtils.WX_OPEN_APP_ID,
                wxUrl,
                "李瑞最帅"
        );
        System.out.println(baseUrl);
        return "redirect:" + url;
    }


}
