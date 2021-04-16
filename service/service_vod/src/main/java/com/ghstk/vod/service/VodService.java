package com.ghstk.vod.service;

import org.springframework.web.multipart.MultipartFile;

public interface VodService {
    String uploadFile(MultipartFile file);

    boolean deleteFile(String vodId);

    String getFileInfo(String vodId);

    String getVideoAuth(String vodId);
}
