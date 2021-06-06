package com.atguigu.yygh.user.service;

import com.atguigu.yygh.model.user.Patient;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Auther :朱树广
 * @Date :2021/6/5
 * @Description :com.atguigu.yygh.user.service
 * @Version :1.0
 */
public interface PatientService extends IService<Patient> {
    List<Patient> findAll(Long userId);

    Patient getPatientId(Long id);
}
