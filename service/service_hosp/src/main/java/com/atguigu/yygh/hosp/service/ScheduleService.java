package com.atguigu.yygh.hosp.service;

import com.atguigu.yygh.model.hosp.Schedule;
import com.atguigu.yygh.vo.hosp.ScheduleQueryVo;
import org.springframework.data.domain.Page;

import java.util.Map;

/**
 * @Auther :朱树广
 * @Date :2021/5/29
 * @Description :com.atguigu.yygh.hosp.service
 * @Version :1.0
 */
public interface ScheduleService {
    void save(Map<String, Object> map);

    Page<Schedule> findPageSchedule(int page, int limit, ScheduleQueryVo scheduleQueryVo);

    void remove(String hoscode, String hosScheduleId);
}
