package com.atguigu.yygh.order;

import com.atguigu.yygh.vo.order.OrderCountQueryVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * @Auther :朱树广
 * @Date :2021/6/13
 * @Description :com.atguigu.yygh.order
 * @Version :1.0
 */
@Repository
@FeignClient("service-order")
public interface OrderFeignClient {

    @PostMapping("/api/order/orderInfo/inner/getCountMap")
    Map<String, Object> getCountMap(@RequestBody OrderCountQueryVo orderCountQueryVo);
}
