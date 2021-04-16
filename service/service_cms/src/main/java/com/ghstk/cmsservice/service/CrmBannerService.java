package com.ghstk.cmsservice.service;

import com.ghstk.cmsservice.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author ghStk
 * @since 2021-04-11
 */
public interface CrmBannerService extends IService<CrmBanner> {

    List<CrmBanner> getPageBanner(long current, long size);

    List<CrmBanner> getIndexBanner(int limit);
}
