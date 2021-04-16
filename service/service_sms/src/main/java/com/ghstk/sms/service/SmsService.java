package com.ghstk.sms.service;

import java.util.Map;

public interface SmsService {

    boolean sendByAliyun(Map<String, Object> param);
    boolean sendByTencent(Map<String, Object> param);

}
