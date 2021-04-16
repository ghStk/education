package com.ghstk.sms.controller;


import com.ghstk.commonutils.Result;
import com.ghstk.sms.service.SmsService;
import com.ghstk.sms.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/smsservice")
@CrossOrigin
public class SmsController {

    @Autowired
    SmsService service;

    @Autowired
    RedisTemplate<String, String> redis;


    @PostMapping("/send")
    public Result send(@RequestBody Map<String, String> params) {
        String phone = params.get("mobile");
        System.out.println("[Sms] params: " + params);
        // 从redis获取
        String code = redis.opsForValue().get(phone);
        if (code != null) {
            return Result.ok();
        }
        // 生成验证码
        code = RandomUtil.getFourBitRandom();
        HashMap<String, Object> param = new HashMap<>();
        param.put("code", code);
        param.put("phone", phone);
        if (service.sendByTencent(param)) {
            redis.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);
            return Result.ok();
        }
        return Result.ng().message("发送短信失败");
    }


}
