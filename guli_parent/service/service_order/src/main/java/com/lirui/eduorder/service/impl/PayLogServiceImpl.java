package com.lirui.eduorder.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.wxpay.sdk.WXPayUtil;
import com.lirui.eduorder.entity.Order;
import com.lirui.eduorder.entity.PayLog;
import com.lirui.eduorder.mapper.PayLogMapper;
import com.lirui.eduorder.service.OrderService;
import com.lirui.eduorder.service.PayLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lirui.eduorder.utils.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2023-06-01
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {

    @Autowired
    OrderService orderService;
    //生成微信支付二维码接口
    @Override
    public Map createNative(String orderNo) throws Exception {
        //1 根据订单号查询订单信息
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderNo);
        Order order = orderService.getOne(wrapper);

        //2 使用map设置生成二维码需要参数
        Map m = new HashMap();
        m.put("appid","wx74862e0dfcf69954");//关联的公众号
        m.put("mch_id", "1558950191");//商户号
        m.put("nonce_str", WXPayUtil.generateNonceStr());//是二维码一直在变
        m.put("body", order.getCourseTitle()); //课程标题
        m.put("out_trade_no", orderNo); //订单号
        m.put("total_fee", order.getTotalFee().multiply(new BigDecimal("100")).longValue()+"");//订单中的价格
        m.put("spbill_create_ip", "127.0.0.1");//域名地址
        m.put("notify_url", "http://guli.shop/api/order/weixinPay/weixinNotify\n");//回调地址
        m.put("trade_type", "NATIVE");//支付的类型

        //发送Httpcilent请求
        HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");//微信支付的固定地址
        //将map集合转换成xml文件,将m里的数值进行加密，商户key就是为了加密用的,并传入
        client.setXmlParam(WXPayUtil.generateSignedXml(m,"商户key"));
        //设置支持https协议
        client.setHttps(true);
        //执行post请求
        client.post();
        //得到发送请求返回结果
        //返回内容，使用xml格式放回
        String content = client.getContent();

        //把xml格式转换为map集合，把集合返回
        Map<String, String> resultMap = WXPayUtil.xmlToMap(content);

        //最终返回数据 的封装
        Map map = new HashMap();
        map.put("out_trade_no", orderNo);
        map.put("course_id", order.getCourseId());
        map.put("total_fee", order.getTotalFee());
        map.put("result_code", resultMap.get("result_code"));  //返回二维码操作状态码
        map.put("code_url", resultMap.get("code_url"));        //二维码地址

        return map;
    }
}
