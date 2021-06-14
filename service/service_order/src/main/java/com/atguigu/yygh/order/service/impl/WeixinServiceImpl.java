package com.atguigu.yygh.order.service.impl;

import com.atguigu.yygh.enums.PaymentTypeEnum;
import com.atguigu.yygh.model.order.OrderInfo;
import com.atguigu.yygh.order.service.OrderService;
import com.atguigu.yygh.order.service.PaymentService;
import com.atguigu.yygh.order.service.WeixinService;
import com.atguigu.yygh.order.utils.ConstantPropertiesUtils;
import com.atguigu.yygh.order.utils.HttpClient;
import com.github.wxpay.sdk.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Auther :朱树广
 * @Date :2021/6/9
 * @Description :com.atguigu.yygh.order.service.impl
 * @Version :1.0
 */
@Service
public class WeixinServiceImpl implements WeixinService {

    @Autowired
    private OrderService orderService;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 根据订单号下单，生成支付链接
     */
    @Override
    public Map createNative(Long orderId) throws Exception {
        Map redisMap = (Map) redisTemplate.opsForValue().get(orderId.toString());
        if (redisMap != null) {
            return redisMap;
        }
        //1.根据orderId获取订单信息
        OrderInfo order = orderService.getById(orderId);
        //2.向订单记录表添加记录
        paymentService.savePaymentInfo(order, PaymentTypeEnum.WEIXIN.getStatus());
        //3、设置参数
        Map paramMap = new HashMap();
        paramMap.put("appid", ConstantPropertiesUtils.APPID);
        paramMap.put("mch_id", ConstantPropertiesUtils.PARTNER);
        paramMap.put("nonce_str", WXPayUtil.generateNonceStr());
        String body = order.getReserveDate() + "就诊" + order.getDepname();
        paramMap.put("body", body);
        paramMap.put("out_trade_no", order.getOutTradeNo());
        //paramMap.put("total_fee", order.getAmount().multiply(new BigDecimal("100")).longValue()+"");
        paramMap.put("total_fee", "1");
        paramMap.put("spbill_create_ip", "127.0.0.1");
        paramMap.put("notify_url", "http://guli.shop/api/order/weixinPay/weixinNotify");
        paramMap.put("trade_type", "NATIVE");
        //4、HTTPClient来根据URL访问第三方接口并且传递参数
        HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
        //设置map参数
        client.setXmlParam(WXPayUtil.generateSignedXml(paramMap, ConstantPropertiesUtils.PARTNERKEY));
        client.setHttps(true);
        client.post();
        //5.返回相关数据
        String xml = client.getContent();
        Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
        //6.封装返回结果集
        Map map = new HashMap();
        map.put("orderId", orderId);
        map.put("totalFee", order.getAmount());
        map.put("resultCode", resultMap.get("result_code"));
        map.put("codeUrl", resultMap.get("code_url"));
        if (resultMap.get("code_url") != null) {
            redisTemplate.opsForValue().set(orderId.toString(), map,120, TimeUnit.MINUTES);
        }
        return map;
    }

    @Override
    public Map<String, String> queryPayStatus(Long orderId) {
        try {
            //1。根据订单id获取订单信息
            OrderInfo orderInfo = orderService.getById(orderId);
            //2.封装参数
            Map paramMap = new HashMap<>();
            paramMap.put("appid", ConstantPropertiesUtils.APPID);
            paramMap.put("mch_id", ConstantPropertiesUtils.PARTNER);
            paramMap.put("out_trade_no", orderInfo.getOutTradeNo());
            paramMap.put("nonce_str", WXPayUtil.generateNonceStr());
            //3.设置请求内容
            HttpClient httpClient = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            httpClient.setXmlParam(WXPayUtil.generateSignedXml(paramMap, ConstantPropertiesUtils.PARTNERKEY));
            httpClient.setHttps(true);
            httpClient.post();

            String content = httpClient.getContent();
            Map<String, String> stringStringMap = WXPayUtil.xmlToMap(content);
            return stringStringMap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
