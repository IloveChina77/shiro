package com.zr.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value="/test", tags= "测试接口模块")
@RestController
@RequestMapping("/test")
public class HelloController {

    @ApiOperation(value = "首页", notes = "展示首页")
    @GetMapping("/")
    public String index() {
        return "/index.html";
    }

    @GetMapping("/name")
    public String showNames() {
        return "What's your name";
    }




}
