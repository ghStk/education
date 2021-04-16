package com.ghstk.usrservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ghstk.commonutils.utils.JwtUtils;
import com.ghstk.commonutils.utils.MD5;
import com.ghstk.servicebase.exceptionhandler.MyException;
import com.ghstk.usrservice.entity.UserMember;
import com.ghstk.usrservice.entity.vo.LoginVo;
import com.ghstk.usrservice.entity.vo.RegistryVo;
import com.ghstk.usrservice.mapper.UserCenterMapper;
import com.ghstk.usrservice.service.UserCenterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author ghStk
 * @since 2021-04-13
 */
@Service
public class UserCenterServiceImpl extends ServiceImpl<UserCenterMapper, UserMember> implements UserCenterService {

    @Autowired
    RedisTemplate<String, String> redis;

    @Override
    public String login(LoginVo loginVo) {
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        // 参数校验
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new MyException(20001, "用户名或密码错误");
        }
        // 从数据库查询
        UserMember userMember = baseMapper
                .selectOne(new QueryWrapper<UserMember>().eq("mobile", mobile));
        if (userMember == null) {
            throw new MyException(20001, "用户名或密码错误");
        }
        // 密码校验
        String md5 = MD5.encrypt(password);
        if (!md5.equals(userMember.getPassword())) {
            throw new MyException(20001, "用户名或密码错误");
        }
        // 权限检查
        if (userMember.getIsDisabled()) {
            throw new MyException(20001, "用户已被禁用");
        }
        // 发放token
        return JwtUtils.getJwtToken(userMember.getId(), userMember.getNickname());
    }

    @Override
    public void registry(RegistryVo registryVo) {
        String code = registryVo.getCode();
        String mobile = registryVo.getMobile();
        String nickname = registryVo.getNickname();
        String password = registryVo.getPassword();

        // 参数校验
        if (StringUtils.isEmpty(code) || StringUtils.isEmpty(mobile)
                || StringUtils.isEmpty(nickname) || StringUtils.isEmpty(password)) {
            throw new MyException(20001, "注册信息不能为空");
        }

        // 验证码校验
        String authCode = redis.opsForValue().get(mobile);
//        authCode = "000111";
        if (!code.equals(authCode)) {
            throw new MyException(20001, "验证码错误");
        }

        // 重复注册校验
        Integer mobileCount = baseMapper
                .selectCount(new QueryWrapper<UserMember>().eq("mobile", mobile));
        if (mobileCount != 0) {
            throw new MyException(20001, "此手机号已被注册");
        }

        // 开始注册
        UserMember member = new UserMember();
        member.setNickname(nickname);
        member.setMobile(mobile);
        member.setPassword(MD5.encrypt(password));
        member.setIsDisabled(false);

        this.save(member);
    }

    @Override
    public UserMember getByOpenId(String openid) {
        QueryWrapper<UserMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",openid);
        return baseMapper.selectOne(wrapper);
    }
}
