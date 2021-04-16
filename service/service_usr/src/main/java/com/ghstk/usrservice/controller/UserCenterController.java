package com.ghstk.usrservice.controller;


import com.ghstk.commonutils.Result;
import com.ghstk.commonutils.utils.JwtUtils;
import com.ghstk.usrservice.entity.UserMember;
import com.ghstk.usrservice.entity.vo.LoginVo;
import com.ghstk.usrservice.entity.vo.RegistryVo;
import com.ghstk.usrservice.service.UserCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author ghStk
 * @since 2021-04-13
 */
@RestController
@RequestMapping("/usrservice")
@CrossOrigin
public class UserCenterController {

    @Autowired
    UserCenterService service;

    @PostMapping("/login")
    public Result login(@RequestBody LoginVo loginVo) {
        String token = service.login(loginVo);
        return Result.ok().data("token", token);
    }

    @PostMapping("/registry")
    public Result registry(@RequestBody RegistryVo registryVo) {
        service.registry(registryVo);
        return Result.ok();
    }

    @GetMapping("/getMemberInfo")
    public Result getMemberInfo(HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        UserMember member = service.getById(memberId);
        return Result.ok().data("memberInfo", member);
    }

}

