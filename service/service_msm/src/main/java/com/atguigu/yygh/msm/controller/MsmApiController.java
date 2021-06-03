package com.atguigu.yygh.msm.controller;

import com.atguigu.yygh.common.result.Result;
import com.atguigu.yygh.msm.service.MsmService;
import com.atguigu.yygh.msm.utils.RandomUtil;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @Auther :朱树广
 * @Date :2021/6/2
 * @Description :com.atguigu.yygh.msm.controller
 * @Version :1.0
 */
@Api(tags = "短信接口")
@RestController
@RequestMapping("/api/msm")
public class MsmApiController {
    @Autowired
    private MsmService msmService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    //发送手机验证码
    @GetMapping("send/{phone}")
    public Result send(@PathVariable String phone) {
        String code = redisTemplate.opsForValue().get("phone");
        if (!StringUtils.isEmpty(code)) {
            return Result.ok();
        }
        //生成验证码
        //code = RandomUtil.getSixBitRandom();
        //boolean isSend = msmService.send(phone, code);
        //未开通阿里云短信，模拟生成短信验证码
        code = "123456";
        boolean isSend = true;
        if (isSend) {
            redisTemplate.opsForValue().set(phone, code, 2L, TimeUnit.MINUTES);
            return Result.ok();
        } else {
            return Result.fail("短信验证码发送失败");
        }
    }

}
