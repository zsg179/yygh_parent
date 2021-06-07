package com.atguigu.yygh.order.service;

import com.atguigu.yygh.model.order.OrderInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author zhusg02
 * @date 2021/6/7 10:49
 */
public interface OrderService extends IService<OrderInfo> {
    Long saveOrder(String scheduleId, Long patientId);
}
