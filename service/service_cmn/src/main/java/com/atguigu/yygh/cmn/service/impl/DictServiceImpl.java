package com.atguigu.yygh.cmn.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.yygh.cmn.listener.DictListener;
import com.atguigu.yygh.cmn.mapper.DictMapper;
import com.atguigu.yygh.cmn.service.DictService;
import com.atguigu.yygh.model.cmn.Dict;
import com.atguigu.yygh.vo.cmn.DictEeVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhusg02
 * @date 2021/5/25 14:13
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

    @Cacheable(value = "dict", keyGenerator = "keyGenerator")
    @Override
    public List<Dict> findChildData(Long id) {
        QueryWrapper<Dict> wrapper = new QueryWrapper<Dict>().eq("parent_id", id);
        List<Dict> list = baseMapper.selectList(wrapper);
        for (Dict dict : list) {
            boolean hasChildren = hasChildren(dict.getId());
            dict.setHasChildren(hasChildren);
        }
        return list;
    }

    @Override
    public void exportData(HttpServletResponse response) throws Exception {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码
        String fileName = URLEncoder.encode("数据字典", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        List<Dict> dictList = baseMapper.selectList(null);
        List<DictEeVo> dictEeVoList = new ArrayList<>();
        for (Dict dict : dictList) {
            DictEeVo dictEeVo = new DictEeVo();
            BeanUtils.copyProperties(dict, dictEeVo);
            dictEeVoList.add(dictEeVo);
        }
        EasyExcel.write(response.getOutputStream(), DictEeVo.class)
                .sheet("数据字典")
                .doWrite(dictEeVoList);
    }

    @CacheEvict(value = "dict", allEntries = true)
    @Override
    public void importData(MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), DictEeVo.class, new DictListener(baseMapper)).sheet().doRead();
    }

    @Override
    public String getDictName(String dictCode, String value) {
        if (dictCode.equals("")) {
            //通过value查询
            QueryWrapper<Dict> wrapper = new QueryWrapper<>();
            wrapper.eq("value", value);
            Dict dict = baseMapper.selectOne(wrapper);
            return dict.getName();
        } else {

            Long parentId = getDictByDictCode(dictCode).getId();
            QueryWrapper<Dict> wrapper = new QueryWrapper<>();
            wrapper.eq("parent_id", parentId).eq("value", value);
            Dict dict = baseMapper.selectOne(wrapper);
            return dict.getName();
        }
    }

    @Override
    public List<Dict> findByDictCode(String dictCode) {
        Dict dict = this.getDictByDictCode(dictCode);
        List<Dict> list = this.findChildData(dict.getId());
        return list;
    }

    private Dict getDictByDictCode(String dictCode) {
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("dict_code", dictCode);
        Dict dict = baseMapper.selectOne(wrapper);
        return dict;
    }


    private boolean hasChildren(Long id) {
        Integer count = baseMapper.selectCount(new QueryWrapper<Dict>().eq("parent_id", id));
        return count > 0;
    }
}
