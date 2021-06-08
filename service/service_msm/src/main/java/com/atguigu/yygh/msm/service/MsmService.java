package com.atguigu.yygh.msm.service;

import com.atguigu.yygh.vo.msm.MsmVo;

/**
 * @Auther :朱树广
 * @Date :2021/6/2
 * @Description :com.atguigu.yygh.msm.service
 * @Version :1.0
 */
public interface MsmService {
    boolean send(String phone, String code);

    boolean send(MsmVo msmVo);
}
