package com.atguigu.yygh.hosp.repository;

import com.atguigu.yygh.model.hosp.Hospital;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author zhusg02
 * @date 2021/5/27 11:26
 */
@Repository
public interface HospitalRepository extends MongoRepository<Hospital,String> {
    //无实现方法，严格按照命名规则
    Hospital getHospitalByHoscode(String hoscode);
}
