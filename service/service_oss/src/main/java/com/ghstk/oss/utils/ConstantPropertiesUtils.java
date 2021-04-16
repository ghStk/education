package com.ghstk.oss.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConstantPropertiesUtils implements InitializingBean {

    @Value("${aliyun.oss.file.endpoint}")
    private String ossEndpoint;

    @Value("${aliyun.oss.file.bucketname}")
    private String ossBucketName;

    @Value("${aliyun.oss.file.keyid}")
    private String ossKeyId;

    @Value("${aliyun.oss.file.keysecret}")
    private String ossKeySecret;

    public static String OSS_END_POINT;
    public static String OSS_BUCKET_NAME;
    public static String OSS_KEY_ID;
    public static String OSS_KEY_SECRET;


    @Override
    public void afterPropertiesSet() throws Exception {
        OSS_END_POINT = ossEndpoint;
        OSS_BUCKET_NAME = ossBucketName;
        OSS_KEY_ID = ossKeyId;
        OSS_KEY_SECRET = ossKeySecret;
    }
}
