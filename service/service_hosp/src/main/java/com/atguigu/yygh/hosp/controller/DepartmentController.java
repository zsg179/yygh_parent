package com.atguigu.yygh.hosp.controller;

import com.atguigu.yygh.common.result.Result;
import com.atguigu.yygh.hosp.service.DepartmentService;
import com.atguigu.yygh.vo.hosp.DepartmentVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zhusg02
 * @date 2021/5/31 10:57
 */
@RestController
@RequestMapping("/admin/hosp/department")
@CrossOrigin
@Api(tags = "科室管理")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @ApiOperation("查询医院所有科室列表")
    @GetMapping("/getDeptList/{hoscode}")
    public Result getDeptList(@PathVariable("hoscode") String hoscode) {
        List<DepartmentVo> list = departmentService.getDeptList(hoscode);
        return Result.ok(list);
    }
}
