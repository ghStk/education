package com.ghstk.eduservice.controller;


import com.ghstk.commonutils.Result;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/eduservice/user")
public class EduLoginController {

    @PostMapping("/login")
    @CrossOrigin
    public Result login() {
        return Result.ok().data("token","ghstkWin");
    }

    @GetMapping("/info")
    @CrossOrigin
    public Result info() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("roles","老哥");
        map.put("name","小王");
        map.put("avatar","https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3266448777,258862184&fm=26&gp=0.jpg");

        return Result.ok().data(map);
    }

}
