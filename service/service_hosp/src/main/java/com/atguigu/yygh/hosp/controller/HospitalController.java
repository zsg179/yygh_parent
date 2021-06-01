package com.atguigu.yygh.hosp.controller;

import com.atguigu.yygh.common.result.Result;
import com.atguigu.yygh.hosp.service.HospitalService;
import com.atguigu.yygh.model.hosp.Hospital;
import com.atguigu.yygh.vo.hosp.HospitalQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Auther :朱树广
 * @Date :2021/5/29
 * @Description :com.atguigu.yygh.hosp.controller
 * @Version :1.0
 */
@Api(tags = "医院管理")
@RestController
@RequestMapping("/admin/hosp/hospital")
public class HospitalController {
    @Autowired
    private HospitalService hospitalService;

    @ApiOperation("分页查询医院列表")
    @GetMapping("/list/{page}/{limit}")
    public Result listHosp(@PathVariable("page") Integer page
            , @PathVariable("limit") Integer limit
            , HospitalQueryVo hospitalQueryVo) {
        Page<Hospital> pageModel = hospitalService.selectHospPage(page, limit, hospitalQueryVo);
        return Result.ok(pageModel);
    }

    @ApiOperation("调整医院状态")
    @PostMapping("updateStatus/{id}/{status}")
    public Result updateStatus(@PathVariable("id") String id
            , @PathVariable("status") Integer status) {
        hospitalService.updateStatus(id, status);
        return Result.ok();
    }

    @ApiOperation("查看医院详情")
    @GetMapping("show/{id}")
    public Result show(@PathVariable("id") String id) {
        Map<String ,Object> map = hospitalService.show(id);
        return Result.ok(map);
    }
}
