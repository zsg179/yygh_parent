package com.atguigu.yygh.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author zhusg02
 * @date 2021/6/4 15:04
 */
public interface FileService {
    String upload(MultipartFile file);
}
