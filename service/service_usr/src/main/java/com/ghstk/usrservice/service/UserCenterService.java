package com.ghstk.usrservice.service;

import com.ghstk.usrservice.entity.UserMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ghstk.usrservice.entity.vo.LoginVo;
import com.ghstk.usrservice.entity.vo.RegistryVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author ghStk
 * @since 2021-04-13
 */
public interface UserCenterService extends IService<UserMember> {

    String login(LoginVo loginVo);

    void registry(RegistryVo registryVo);

    UserMember getByOpenId(String openid);
}
