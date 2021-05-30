package com.atguigu.yygh.cmn.service;

import com.atguigu.yygh.model.cmn.Dict;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author zhusg02
 * @date 2021/5/25 14:12
 */
public interface DictService extends IService<Dict> {
    List<Dict> findChildData(Long id);


    void exportData(HttpServletResponse response) throws Exception;

    void importData(MultipartFile file) throws IOException;

    String getDictName(String parentDictCode, String value);

    List<Dict> findByDictCode(String dictCode);
}
