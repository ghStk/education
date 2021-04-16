package com.ghstk.eduservice.client;


import com.ghstk.commonutils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "service-vod",fallback = VodClientImpl.class)
@Component
public interface VodClient {

    // 删除视频
    @DeleteMapping("/vodservice/{vodId}")
    Result deleteVideo(@PathVariable("vodId") String vodId);

    // 删除视频 批量
    @DeleteMapping("/vodservice/deleteVideoBatch")
    Result deleteVideoBatch(@RequestBody List<String> vodIdList);
}
