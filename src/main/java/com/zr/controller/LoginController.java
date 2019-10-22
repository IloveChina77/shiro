package com.zr.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @PostMapping("/doLogin")
    public String doLogin(String username, String password) {

        Subject subject = SecurityUtils.getSubject();
        subject.login(new UsernamePasswordToken(username, password));
        return "you have login now";
    }

    @GetMapping("/hehe")
    public String sayHeHe(){
        return "呵呵，就完事了!!";
    }

    @GetMapping("/doOperation")
    @RequiresPermissions(value = {"/doo"})
    public String doOperation(){
        return "Do operation!!!";
    }

    @GetMapping("/unauthorizedUrl")
    public String error(){
        return "error";
    }

    @GetMapping("/logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "成功登出";
    }


}
