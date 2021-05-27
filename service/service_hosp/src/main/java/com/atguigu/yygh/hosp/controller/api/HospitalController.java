package com.atguigu.yygh.hosp.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.yygh.common.helper.HttpRequestHelper;
import com.atguigu.yygh.common.result.Result;
import com.atguigu.yygh.hosp.service.HospitalService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
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
public class HospitalController {
    @Autowired
    private HospitalService hospitalService;


    @PostMapping("saveHospital")
    public Result saveHosp(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> map = HttpRequestHelper.switchMap(parameterMap);
        hospitalService.save(map);
        return Result.ok();

    }
}
