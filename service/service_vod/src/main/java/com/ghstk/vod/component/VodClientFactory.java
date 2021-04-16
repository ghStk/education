package com.ghstk.vod.component;

import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;

import static com.ghstk.vod.utils.VodPropertiesUtil.*;


public class VodClientFactory {

    // 获取client
    public static DefaultAcsClient getClient() throws ClientException {
        DefaultProfile profile = DefaultProfile.getProfile(REGION_ID, KEY_ID, KEY_SECRET);
        return new DefaultAcsClient(profile);
    }

}
