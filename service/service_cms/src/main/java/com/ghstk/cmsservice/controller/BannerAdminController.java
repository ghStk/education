package com.ghstk.cmsservice.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ghstk.cmsservice.entity.CrmBanner;
import com.ghstk.cmsservice.service.CrmBannerService;
import com.ghstk.commonutils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/cmsservice/bannerAdmin")
@CrossOrigin
public class BannerAdminController {

    @Autowired
    CrmBannerService service;

    // 添加
    @PostMapping
    public Result saveBanner(@RequestBody CrmBanner banner) {
        service.save(banner);
        return Result.ok();
    }

    // 删除
    @DeleteMapping("/{bannerId}")
    public Result deleteBanner(@PathVariable String bannerId) {
        service.removeById(bannerId);
        return Result.ok();
    }

    // 修改
    @PostMapping("/updateBanner")
    public Result updateBanner(@RequestBody CrmBanner banner) {
        service.updateById(banner);
        return Result.ok();
    }

    // 获取分页列表
    @GetMapping("/{current}/{size}")
    public Result getPageBanner(@PathVariable long current, @PathVariable long size) {
        List<CrmBanner> pageBanner = service.getPageBanner(current, size);
        return Result.ok()
                .data("items", pageBanner)
                .data("total", pageBanner.size());
    }


}

