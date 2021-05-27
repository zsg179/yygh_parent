package com.atguigu.yygh.hosp.service;


import com.atguigu.yygh.model.hosp.Hospital;

import java.util.Map;

/**
 * @author zhusg02
 * @date 2021/5/27 11:32
 */
public interface HospitalService {
    void save(Map<String, Object> map);

    Hospital getByHoscode(String hoscode);
}
