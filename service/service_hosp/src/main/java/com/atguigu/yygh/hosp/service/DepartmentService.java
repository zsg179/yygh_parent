package com.atguigu.yygh.hosp.service;

import com.atguigu.yygh.model.hosp.Department;
import com.atguigu.yygh.vo.hosp.DepartmentQueryVo;
import org.springframework.data.domain.Page;

import java.util.Map;

/**
 * @Auther :朱树广
 * @Date :2021/5/29
 * @Description :com.atguigu.yygh.hosp.service
 * @Version :1.0
 */
public interface DepartmentService {
    void save(Map<String, Object> map);

    Page<Department> findPageDepartment(int page, int limit, DepartmentQueryVo departmentQueryVo);

    void remove(String hoscode, String depcode);
}
