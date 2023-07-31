package com.lirui.msmservice.service.impl;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.lirui.msmservice.service.MsmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @author ：xxx
 * @description：TODO
 * @date ：2023/5/28 17:39
 */
@Service
public class MsmServiceImpl implements MsmService {


    @Override
    public boolean send(String phone, Map<String, Object> param) {
        if(StringUtils.isEmpty(phone)){
            return false;
        };

        DefaultProfile profile =
                DefaultProfile.getProfile("default", "LTAI5tM83ptKjXSPq1kB1Yc6", "DouODuOljS8NmU0UuwF7Xz5RrYDPnE");
        IAcsClient client = new DefaultAcsClient(profile);

        //设置相关固定的参数
        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");

        //设置相关参数
        request.putQueryParameter("PhoneNumbers",phone);
        request.putQueryParameter("SignName","我的学成在线教育网站");//申请阿里云 签名名称
        request.putQueryParameter("TemplateCode","SMS_461045298"); //申请阿里云 模板code
        request.putQueryParameter("TemplateParam", JSON.toJSONString(param));//向阿里云传输验证码，但是需要的是json数据不是普通的字符串，这里借用JSON工具包可以直接将map集合转换为json数据


        try {

            //最终发送短信
            CommonResponse response = client.getCommonResponse(request);
            //获取返回的值是成功还是失败
            boolean success = response.getHttpResponse().isSuccess();
            return success;
        }catch (Exception e){
            
            e.printStackTrace();
            return false;
        }

    }
}
