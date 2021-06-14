package com.atguigu.yygh.order.mapper;

import com.atguigu.yygh.model.order.PaymentInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zhusg02
 * @date 2021/6/7 10:48
 */
@Mapper
public interface PaymentMapper extends BaseMapper<PaymentInfo> {
}
