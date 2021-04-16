package com.ghstk.usrservice.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ghstk.commonutils.utils.JwtUtils;
import com.ghstk.usrservice.entity.UserMember;
import com.ghstk.usrservice.service.UserCenterService;
import com.ghstk.usrservice.utils.HttpClientUtils;
import com.ghstk.usrservice.utils.WxProperties;
import com.google.gson.Gson;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

@Controller
@CrossOrigin
@RequestMapping("/api/ucenter/wx")
public class WxApiController {

    @Autowired
    UserCenterService userCenterService;

    // 扫码成功后回调
    @GetMapping("/callback")
    public String callback(String code, String state) {
        String token = "";
        Gson gson = new Gson();

        try {
            // 1. 获取 access_token 和 openid
            String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";
            String accessTokenUrl = String.format(
                    baseAccessTokenUrl,
                    WxProperties.APP_ID,
                    WxProperties.APP_SECRET,
                    code);
            String accessTokenInfo = HttpClientUtils.get(accessTokenUrl);

            // 1.1 处理accessTokenInfo
            HashMap accessTokenMap = gson.fromJson(accessTokenInfo, HashMap.class);
            String access_token = (String) accessTokenMap.get("access_token");
            String openid = (String) accessTokenMap.get("openid");
            System.out.println(accessTokenMap);

            // 2. 获取 nickname headimgurl
            UserMember member = userCenterService.getByOpenId(openid);
            if (member == null) {
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                String userInfoUrl = String.format(
                        baseUserInfoUrl,
                        access_token,
                        openid
                );
                String userInfo = HttpClientUtils.get(userInfoUrl);
                HashMap userInfoMap = gson.fromJson(userInfo, HashMap.class);
                String nickname = (String) userInfoMap.get("nickname");//昵称
                String avatarUrl = (String) userInfoMap.get("headimgurl");//头像
                System.out.println(userInfoMap);

                // 保存到数据库
                member = new UserMember();
                member.setOpenid(openid);
                member.setNickname(nickname);
                member.setAvatar(avatarUrl);
                userCenterService.save(member);
            }
            // 3. 获取本应用token
            token = JwtUtils.getJwtToken(member.getId(), member.getNickname());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:http://localhost:3000?token=" + token;

    }

    // 跳转到微信二维码页面
    @GetMapping("/login")
    public String login() {
        String bathUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";
        String redirectUrl = null;
        try {
            redirectUrl = URLEncoder.encode(WxProperties.REDIRECT_URL, "utf-8");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = String.format(bathUrl,
                WxProperties.APP_ID,
                redirectUrl,
                "QRLogin");
        return "redirect:" + url;
    }


}
