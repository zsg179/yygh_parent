package com.atguigu.yygh.order.service;

import java.util.Map;

/**
 * @Auther :朱树广
 * @Date :2021/6/9
 * @Description :com.atguigu.yygh.order.service
 * @Version :1.0
 */
public interface WeixinService {
    Map createNative(Long orderId) throws Exception;


    Map<String, String> queryPayStatus(Long orderId) ;
}
