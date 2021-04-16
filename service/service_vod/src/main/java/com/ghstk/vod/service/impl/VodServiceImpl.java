package com.ghstk.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.*;
import com.ghstk.vod.component.VodClientFactory;
import com.ghstk.vod.service.VodService;
import com.ghstk.vod.utils.VodPropertiesUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.InputStream;

@Service
public class VodServiceImpl implements VodService {

    @Override
    public String uploadFile(MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            String title = String.valueOf(System.currentTimeMillis());
            InputStream inputStream = file.getInputStream();

            UploadStreamRequest request = new UploadStreamRequest(VodPropertiesUtil.KEY_ID, VodPropertiesUtil.KEY_SECRET, title, fileName, inputStream);
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);

            String videoId = null;
            if (response.isSuccess()) {
                videoId = response.getVideoId();
            } else {
                // 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                videoId = response.getVideoId();
            }
            return videoId;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteFile(String vodId) {
        try {
            // 获取client
            DefaultAcsClient client = VodClientFactory.getClient();
            // 设置请求
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(vodId); //支持传入多个视频ID，多个用逗号分隔
            // 设置回复
            DeleteVideoResponse response = client.getAcsResponse(request);
            System.out.println(response.getRequestId());
        } catch (ClientException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public String getFileInfo(String vodId) {
        GetVideoInfoResponse response = null;
        try {
            // 获取client
            DefaultAcsClient client = VodClientFactory.getClient();
            // 设置请求
            GetVideoInfoRequest request = new GetVideoInfoRequest();
            request.setVideoId(vodId);
            // 设置回复
            response = client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return response != null ? response.getVideo().getTitle() : null;
    }

    @Override
    public String getVideoAuth(String vodId) {
        GetVideoPlayAuthResponse response = null;
        try {
            // 获取client
            DefaultAcsClient client = VodClientFactory.getClient();
            // 设置请求
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            request.setVideoId(vodId);
            // 设置回复
            response = client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return response != null ? response.getPlayAuth() : null;
    }
}
