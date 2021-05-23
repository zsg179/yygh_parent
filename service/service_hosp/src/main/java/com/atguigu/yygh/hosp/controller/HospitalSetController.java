package com.atguigu.yygh.hosp.controller;

import com.atguigu.yygh.common.result.Result;
import com.atguigu.yygh.common.utils.MD5;
import com.atguigu.yygh.hosp.service.HospitalSetService;
import com.atguigu.yygh.model.hosp.HospitalSet;
import com.atguigu.yygh.vo.hosp.HospitalSetQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

/**
 * @Auther :朱树广
 * @Date :2021/5/21
 * @Description :com.atguigu.yygh.hosp.controller
 * @Version :1.0
 */
@Api(tags = "医院设置管理")
@RestController
@RequestMapping("/admin/hosp/hospitalSet")
public class HospitalSetController {

    @Autowired
    private HospitalSetService hospitalSetService;

    @ApiOperation("获取所有医院设置信息")
    @GetMapping("findAll")
    public Result findAllHospitalSet() {
        List<HospitalSet> list = hospitalSetService.list();
        return Result.ok(list);
    }

    @ApiOperation("删除医院设置信息")
    @DeleteMapping("{id}")
    public Result removeHospitalSet(@PathVariable("id") Long id) {
        boolean b = hospitalSetService.removeById(id);
        if (b) {
            return Result.ok();
        }
        return Result.fail();
    }

    @ApiOperation("分页查询医院设置")
    @PostMapping("findPageHospSet/{current}/{limit}")
    public Result findPageHospSet(@PathVariable("current") Long current
            , @PathVariable("limit") Long limit
            , @RequestBody(required = false) HospitalSetQueryVo hospitalSetQueryVo) {
        Page<HospitalSet> page = new Page<>(current, limit);
        QueryWrapper<HospitalSet> wrapper = new QueryWrapper<>();
        String hoscode = hospitalSetQueryVo.getHoscode();
        String hosname = hospitalSetQueryVo.getHosname();
        if (!StringUtils.isEmpty(hoscode)) {
            wrapper.eq("hoscode", hoscode);
        }
        if (!StringUtils.isEmpty(hosname)) {
            wrapper.like("hosname", hosname);
        }
        page = hospitalSetService.page(page, wrapper);
        return Result.ok(page);
    }

    @ApiOperation("添加医院设置")
    @PostMapping("/saveHospitalSet")
    public Result saveHospSet(@RequestBody HospitalSet hospitalSet) {
        Random random = new Random();
        hospitalSet.setSignKey(MD5.encrypt(System.currentTimeMillis() + "" + random.nextInt(1000)));
        //设置状态 1 使用 0 不能使用
        hospitalSet.setStatus(1);
        boolean b = hospitalSetService.save(hospitalSet);
        if (b) {
            return Result.ok();
        }
        return Result.fail();
    }

    @ApiOperation("修改医院设置")
    @PutMapping("/updateHospitalSet")
    public Result updateHospitalSet(@RequestBody HospitalSet hospitalSet) {
        boolean b = hospitalSetService.updateById(hospitalSet);
        if (b) {
            return Result.ok();
        }
        return Result.fail();
    }
}
