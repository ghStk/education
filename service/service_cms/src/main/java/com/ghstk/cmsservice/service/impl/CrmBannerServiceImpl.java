package com.ghstk.cmsservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ghstk.cmsservice.entity.CrmBanner;
import com.ghstk.cmsservice.mapper.CrmBannerMapper;
import com.ghstk.cmsservice.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author ghStk
 * @since 2021-04-11
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    @Override
    public List<CrmBanner> getPageBanner(long current, long size) {
        Page<CrmBanner> page = new Page<>(current, size);
        IPage<CrmBanner> bannerPage = this.page(page, null);
        return bannerPage.getRecords();
    }

    @Cacheable(value = "banner",key = "'selectBannerList'")
    @Override
    public List<CrmBanner> getIndexBanner(int limit) {
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        wrapper.last("limit " + limit);
        wrapper.orderByDesc("gmt_create");
        return this.list(wrapper);
    }
}
