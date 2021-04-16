package com.ghstk.sms.utils;


import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SmsProperties implements InitializingBean {

    @Value("${aliyun.sms.file.keyid}")
    private String keyId;
    @Value("${aliyun.sms.file.keysecret}")
    private String keySecret;
    @Value("${aliyun.sms.file.regionid}")
    private String regionId;
    @Value("${aliyun.sms.file.signname}")
    private String signName;
    @Value("${aliyun.sms.file.templatecode}")
    private String templateCode;

    @Value("${tencent.sms.file.secretId}")
    private String secretId;
    @Value("${tencent.sms.file.secretKey}")
    private String secretKey;
    @Value("${tencent.sms.file.appid}")
    private String appid;
    @Value("${tencent.sms.file.sign}")
    private String sign;
    @Value("${tencent.sms.file.templateID}")
    private String templateID;


    public static String KEY_ID;
    public static String KEY_SECRET;
    public static String REGION_ID;
    public static String SIGN_NAME;
    public static String TEMPLATE_CODE;

    public static String SECRET_ID;
    public static String SECRET_KEY;
    public static String APP_ID;
    public static String SIGN;
    public static String TEMPLATE_ID;

    @Override
    public void afterPropertiesSet() throws Exception {
        // 阿里云
        KEY_ID = keyId;
        KEY_SECRET = keySecret;
        REGION_ID = regionId;
        SIGN_NAME = signName;
        TEMPLATE_CODE = templateCode;
        // 腾讯云
        SECRET_ID = secretId;
        SECRET_KEY = secretKey;
        APP_ID = appid;
        SIGN = sign;
        TEMPLATE_ID = templateID;
    }
}
