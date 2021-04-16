package com.ghstk.cmsservice.controller;

import com.ghstk.cmsservice.entity.CrmBanner;
import com.ghstk.cmsservice.service.CrmBannerService;
import com.ghstk.commonutils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequestMapping("/cmsservice/banner")
@CrossOrigin
public class BannerFrontController {

    @Autowired
    CrmBannerService service;

    // 获取列表
    @GetMapping
    public Result getIndexBanner() {
        List<CrmBanner> indexBanner = service.getIndexBanner(3);
        return Result.ok().data("items",indexBanner);
    }

}

