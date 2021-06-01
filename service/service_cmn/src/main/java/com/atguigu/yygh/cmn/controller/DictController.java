package com.atguigu.yygh.cmn.controller;

import com.atguigu.yygh.cmn.service.DictService;
import com.atguigu.yygh.common.result.Result;
import com.atguigu.yygh.model.cmn.Dict;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author zhusg02
 * @date 2021/5/25 14:14
 */
@Api(tags = "数据字典接口")
@RestController
@RequestMapping("/admin/cmn/dict")
public class DictController {

    @Autowired
    private DictService dictService;

    @ApiOperation("根据数据id查询子数据列表")
    @GetMapping("/findChildData/{id}")
    public Result findChildData(@PathVariable("id") Long id) {
        List<Dict> list = dictService.findChildData(id);
        return Result.ok(list);
    }

    @ApiOperation("数据字典导出")
    @GetMapping("/exportData")
    public void exportData(HttpServletResponse response) throws Exception {
        dictService.exportData(response);
    }

    @ApiOperation("数据字典导入")
    @PostMapping("/importData")
    public Result importData(MultipartFile file) throws Exception {
        dictService.importData(file);
        return Result.ok();
    }

    @ApiOperation("根据dictCode和value获取数据字典名称")
    @GetMapping("/getName/{parentDictCode}/{value}")
    public String getName(@PathVariable("parentDictCode") String parentDictCode,
                          @PathVariable("value") String value) {
        String dictName = dictService.getDictName(parentDictCode, value);
        return dictName;
    }

    @ApiOperation("根据value获取数据字典名称")
    @GetMapping("/getName/{value}")
    public String getName(@PathVariable("value") String value) {
        String dictName = dictService.getDictName("", value);
        return dictName;
    }

    @ApiOperation("根据dictCode获取下级节点")
    @GetMapping("/findByDictCode/{dictCode}")
    public Result findByDictCode(@PathVariable("dictCode") String dictCode) {
       List<Dict> list = dictService.findByDictCode(dictCode);
        return Result.ok(list);
    }
}

