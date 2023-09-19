package com.fanta.ckservice.utils;

import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class SendEmail {
    @Resource
    private MailAccount account;

    public boolean send(String toNumber, String name) {
        try {
            MailUtil.send(account, toNumber, "京东Cookie:" + name + "已过期", "<body style=\"text-align: center; margin-left: auto; margin-right: auto;\">\n" +
                    "  <div id=\"main\" style=\"text-align: center;\">\n" +
                    "    <p>京东Cookie已过期，请通过<a href=\"https://m.jd.com\">m.jd.com(点击跳转)</a>获取最新Cookie</p>\n" +
                    "    <p>更新Cookie网址<a href=\"http://81.69.228.131:9002/\">81.69.228.131:9002(点击跳转)</a></p>\n" +
                    "  </div>\n" +
                    "</body>", true);
        } catch (Exception e) {
            throw new RuntimeException("邮件发送失败");
        }
        return true;
    }

}
