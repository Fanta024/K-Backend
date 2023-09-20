package com.fanta.ckservice.controller;

import com.fanta.ckservice.utils.SendEmail;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Deprecated
@RestController
@RequestMapping("/email")
public class EmailController {
    @Resource
    private SendEmail sendEmail;

    @GetMapping
    public String send(String sk, String to, String ckName) {
        if (sk.equals("sendEmail")) {
            to += "@qq.com";
            boolean send = sendEmail.send(to, ckName);
            if (send) {
                return "发送成功";
            } else return "发送失败";
        } else return "无权限";
    }
}
