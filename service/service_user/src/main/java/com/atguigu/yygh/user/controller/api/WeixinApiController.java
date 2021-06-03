package com.atguigu.yygh.user.controller.api;

import com.atguigu.yygh.common.result.Result;
import com.atguigu.yygh.user.utils.ConstantPropertiesUtil;
import com.atguigu.yygh.user.utils.HttpClientUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhusg02
 * @date 2021/6/3 11:07
 */
@Api(tags = "微信操作接口")
@Controller
@RequestMapping("/api/ucenter/wx")
public class WeixinApiController {

    @ApiOperation("微信回调接口")
    @RequestMapping("callback")
    public String callback(String code, String state) {
        System.out.println(code);
        StringBuffer baseAccessTokenUrl = new StringBuffer()
                .append("https://api.weixin.qq.com/sns/oauth2/access_token")
                .append("?appid=%s")
                .append("&secret=%s")
                .append("&code=%s")
                .append("&grant_type=authorization_code");

        String accessTokenUrl = String.format(baseAccessTokenUrl.toString(),
                ConstantPropertiesUtil.WX_OPEN_APP_ID,
                ConstantPropertiesUtil.WX_OPEN_APP_SECRET,
                code);
        try {
            String accessTokenInfo = HttpClientUtils.get(accessTokenUrl);
            System.out.println(accessTokenInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    @ApiOperation("返回生成二维码相关参数")
    @GetMapping("/getLoginParam")
    @ResponseBody
    public Result genQrConnect() {
        Map<String, Object> map = new HashMap<>();
        try {
            map.put("appid", ConstantPropertiesUtil.WX_OPEN_APP_ID);
            map.put("scope", "snsapi_login");
            String encode = URLEncoder.encode(ConstantPropertiesUtil.WX_OPEN_REDIRECT_URL, "utf-8");
            map.put("redirectUri", encode);
            map.put("state", System.currentTimeMillis() + "");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return Result.ok(map);
    }
}
