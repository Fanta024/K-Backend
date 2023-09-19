package com.fanta.ckservice.config;

import cn.hutool.extra.mail.MailAccount;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConf {
    @Value("${mailServer.host}")
    private String host;
    @Value("${mailServer.port}")
    private int port;
    @Value("${mailServer.from}")
    private String from;
    @Value("${mailServer.username}")
    private String username;
    @Value("${mailServer.password}")
    private String password;

    @Bean
    MailAccount mailAccount() {
        MailAccount account = new MailAccount();
        account.setHost(host);
        account.setPort(port);
        account.setAuth(true);
        account.setFrom(from);
        account.setUser(username);
        account.setPass(password);
        return account;
    }
}
