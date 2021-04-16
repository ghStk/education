package com.ghstk.eduservice.client;

import com.ghstk.commonutils.Result;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VodClientImpl implements VodClient {

    @Override
    public Result deleteVideo(String vodId) {

        return Result.ng().message("熔断器:删除出错了");
    }

    @Override
    public Result deleteVideoBatch(List<String> vodIdList) {
        return Result.ng().message("熔断器:批量删除出错了");
    }
}
