package com.atguigu.yygh.hosp.service.impl;

import com.atguigu.yygh.hosp.mapper.HospitalSetMapper;
import com.atguigu.yygh.hosp.service.HospitalSetService;
import com.atguigu.yygh.model.hosp.HospitalSet;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @Auther :朱树广
 * @Date :2021/5/21
 * @Description :com.atguigu.yygh.hosp.service.impl
 * @Version :1.0
 */
@Service
public class HospitalSetServiceImpl extends ServiceImpl<HospitalSetMapper, HospitalSet> implements HospitalSetService {

    @Override
    public String getSignKey(String hoscode) {
        HospitalSet hospitalSet = baseMapper.selectOne(new QueryWrapper<HospitalSet>().eq("hoscode", hoscode));
        return hospitalSet.getSignKey();
    }
}
