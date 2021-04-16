package com.ghstk.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.ghstk.oss.service.OssService;
import com.ghstk.oss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class OssServiceImpl implements OssService {

    @Override
    public String uploadFile(MultipartFile file) {

        // 登录参数
        String endpoint = ConstantPropertiesUtils.OSS_END_POINT;
        String accessKeyId = ConstantPropertiesUtils.OSS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.OSS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.OSS_BUCKET_NAME;
        String fileUrl = "";

        InputStream inputStream = null;
        OSS ossClient = null;
        try {
            // 创建实例
            ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            // 根据日期分文件夹
            String fileDir = new DateTime().toString("YYYY/MM/");
            String fileName = fileNameGenerator(file.getOriginalFilename());
            String filePath = fileDir + fileName;
            // 上传文件
            inputStream = file.getInputStream();
            ossClient.putObject(bucketName, filePath, inputStream);
            fileUrl = "https://" + bucketName + "." + endpoint + "/" + filePath;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭OSSClient
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return fileUrl;
    }


    private String fileNameGenerator(String originalName) {
        if (originalName == null) return "null";
        String suffix;
        String name;
        int indexOfDot = originalName.lastIndexOf(".");
        if (indexOfDot > 0) {
            name = originalName.substring(0, indexOfDot);
            suffix = originalName.substring(indexOfDot);
        } else {
            name = originalName;
            suffix = "";
        }

        String fileName = name + System.currentTimeMillis();
        fileName = DigestUtils.md5DigestAsHex(fileName.getBytes()) + suffix;
        return fileName;
    }
}
