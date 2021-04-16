package com.ghstk.vod.utils;


import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class VodPropertiesUtil implements InitializingBean {

    @Value("${aliyun.vod.file.keyid}")
    private String keyId;

    @Value("${aliyun.vod.file.keysecret}")
    private String keySecret;

    @Value("${aliyun.vod.file.regionid}")
    private String regionId;

    public static String KEY_ID;
    public static String KEY_SECRET;
    public static String REGION_ID;

    @Override
    public void afterPropertiesSet() throws Exception {
        KEY_ID = keyId;
        KEY_SECRET = keySecret;
        REGION_ID = regionId;
    }
}
