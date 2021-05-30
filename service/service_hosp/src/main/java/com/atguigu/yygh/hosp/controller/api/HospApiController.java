package com.atguigu.yygh.hosp.controller.api;

import com.atguigu.yygh.common.exception.YyghException;
import com.atguigu.yygh.common.helper.HttpRequestHelper;
import com.atguigu.yygh.common.result.Result;
import com.atguigu.yygh.common.result.ResultCodeEnum;
import com.atguigu.yygh.common.utils.MD5;
import com.atguigu.yygh.hosp.service.DepartmentService;
import com.atguigu.yygh.hosp.service.HospitalService;
import com.atguigu.yygh.hosp.service.HospitalSetService;
import com.atguigu.yygh.hosp.service.ScheduleService;
import com.atguigu.yygh.model.hosp.Department;
import com.atguigu.yygh.model.hosp.Hospital;
import com.atguigu.yygh.model.hosp.Schedule;
import com.atguigu.yygh.vo.hosp.DepartmentQueryVo;
import com.atguigu.yygh.vo.hosp.ScheduleQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author zhusg02
 * @date 2021/5/27 11:34
 */
@Api(tags = "医院管理API接口")
@RestController
@RequestMapping("/api/hosp")
public class HospApiController {
    @Autowired
    private HospitalService hospitalService;
    @Autowired
    private HospitalSetService hospitalSetService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private ScheduleService scheduleService;

    @ApiOperation("上传医院")
    @PostMapping("saveHospital")
    public Result saveHosp(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> map = HttpRequestHelper.switchMap(parameterMap);
        String sign = (String) map.get("sign");
        String hoscode = (String) map.get("hoscode");

        String signKey = hospitalSetService.getSignKey(hoscode);
        String encrypt = MD5.encrypt(signKey);
        if (!sign.equals(encrypt)) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }
        String logoData = (String) map.get("logoData");
        logoData = logoData.replaceAll(" ", "+");
        map.put("logoData", logoData);

        hospitalService.save(map);
        return Result.ok();

    }

    @ApiOperation("查询医院")
    @PostMapping("hospital/show")
    public Result getHospital(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> map = HttpRequestHelper.switchMap(parameterMap);
        String sign = (String) map.get("sign");
        String hoscode = (String) map.get("hoscode");
        String signKey = hospitalSetService.getSignKey(hoscode);
        String encrypt = MD5.encrypt(signKey);
        if (!sign.equals(encrypt)) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }

        Hospital hospital = hospitalService.getByHoscode(hoscode);
        return Result.ok(hospital);
    }

    @ApiOperation("上传科室")
    @PostMapping("saveDepartment")
    public Result saveDepartment(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> map = HttpRequestHelper.switchMap(parameterMap);
        String sign = (String) map.get("sign");
        String hoscode = (String) map.get("hoscode");

        String signKey = hospitalSetService.getSignKey(hoscode);
        String encrypt = MD5.encrypt(signKey);
        if (!sign.equals(encrypt)) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }

        departmentService.save(map);
        return Result.ok();
    }

    @ApiOperation("分页查询科室")
    @PostMapping("department/list")
    public Result department(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> map = HttpRequestHelper.switchMap(parameterMap);
        String sign = (String) map.get("sign");
        String hoscode = (String) map.get("hoscode");

        String signKey = hospitalSetService.getSignKey(hoscode);
        String encrypt = MD5.encrypt(signKey);
        if (!sign.equals(encrypt)) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }

        int page = StringUtils.isEmpty(map.get("page")) ? 1 : Integer.parseInt((String) map.get("page"));
        int limit = StringUtils.isEmpty(map.get("limit")) ? 1 : Integer.parseInt((String) map.get("limit"));
        DepartmentQueryVo departmentQueryVo = new DepartmentQueryVo();
        departmentQueryVo.setHoscode(hoscode);
        Page<Department> res = departmentService.findPageDepartment(page, limit, departmentQueryVo);
        return Result.ok(res);
    }

    @ApiOperation("删除科室")
    @PostMapping("department/remove")
    public Result removeDepartment(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> map = HttpRequestHelper.switchMap(parameterMap);
        String sign = (String) map.get("sign");
        String hoscode = (String) map.get("hoscode");
        String depcode = (String) map.get("depcode");
        String signKey = hospitalSetService.getSignKey(hoscode);
        String encrypt = MD5.encrypt(signKey);
        if (!sign.equals(encrypt)) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }

        departmentService.remove(hoscode, depcode);
        return Result.ok();
    }

    @ApiOperation("上传排班")
    @PostMapping("saveSchedule")
    public Result saveSchedule(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> map = HttpRequestHelper.switchMap(parameterMap);
        String sign = (String) map.get("sign");
        String hoscode = (String) map.get("hoscode");

        String signKey = hospitalSetService.getSignKey(hoscode);
        String encrypt = MD5.encrypt(signKey);
        if (!sign.equals(encrypt)) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }

        scheduleService.save(map);
        return Result.ok();
    }

    @ApiOperation("分页查询排班")
    @PostMapping("schedule/list")
    public Result schedule(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> map = HttpRequestHelper.switchMap(parameterMap);
        String sign = (String) map.get("sign");
        String hoscode = (String) map.get("hoscode");
        String depcode = (String) map.get("depcode");
        String signKey = hospitalSetService.getSignKey(hoscode);
        String encrypt = MD5.encrypt(signKey);
        if (!sign.equals(encrypt)) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }

        int page = StringUtils.isEmpty(map.get("page")) ? 1 : Integer.parseInt((String) map.get("page"));
        int limit = StringUtils.isEmpty(map.get("limit")) ? 1 : Integer.parseInt((String) map.get("limit"));

        ScheduleQueryVo scheduleQueryVo = new ScheduleQueryVo();
        scheduleQueryVo.setHoscode(hoscode);
        scheduleQueryVo.setDepcode(depcode);

        Page<Schedule> res = scheduleService.findPageSchedule(page, limit, scheduleQueryVo);
        return Result.ok(res);
    }

    @ApiOperation("删除排班")
    @PostMapping("schedule/remove")
    public Result removeSchedule(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> map = HttpRequestHelper.switchMap(parameterMap);
        String sign = (String) map.get("sign");
        String hoscode = (String) map.get("hoscode");
        String hosScheduleId = (String) map.get("hosScheduleId");
        String signKey = hospitalSetService.getSignKey(hoscode);
        String encrypt = MD5.encrypt(signKey);
        if (!sign.equals(encrypt)) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }

        scheduleService.remove(hoscode, hosScheduleId);
        return Result.ok();
    }
}
