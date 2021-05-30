package com.atguigu.yygh.cmn;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Auther :朱树广
 * @Date :2021/5/29
 * @Description :com.atguigu.yygh.cmn
 * @Version :1.0
 */
@Repository
@FeignClient("service-cmn")
public interface DictFeignClient {

    @GetMapping("/admin/cmn/dict/getName/{parentDictCode}/{value}")
    String getName(@PathVariable("parentDictCode") String parentDictCode,
                          @PathVariable("value") String value);

    @GetMapping("/admin/cmn/dict/getName/{value}")
    String getName(@PathVariable("value") String value);
}
