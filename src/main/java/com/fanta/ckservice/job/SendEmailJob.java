package com.fanta.ckservice.job;

import cn.hutool.core.util.StrUtil;
import com.fanta.ckservice.model.EnvInfo;
import com.fanta.ckservice.utils.QlUtils;
import com.fanta.ckservice.utils.SendEmail;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@EnableScheduling
public class SendEmailJob {
    @Resource
    private QlUtils qlUtils;
    @Resource
    private SendEmail sendEmail;

    @Scheduled(cron = "13 37 23 * * *")
    public void sendEmailTask() {
        System.out.println("执行发送过期CK通知任务");
        List<EnvInfo> jdEnvList = qlUtils.getAllJdEnvs();
        List<EnvInfo> exEnvList = jdEnvList.stream().filter(item -> item.getRemarks() != null)
                .filter(item -> item.getStatus() == 1).collect(Collectors.toList());
        Map<String, List<String>> map = exEnvList.stream().collect(Collectors.groupingBy(EnvInfo::getRemarks, Collectors.mapping(item -> {
                    String value = item.getValue();
                    String[] split = value.split("=");
                    return split[split.length - 1].replace(";", "");
                }
                , Collectors.toList())));
        map.forEach((k, v) ->
                sendEmail.send(k + "@qq.com", StrUtil.sub(v.toString(), 1, -1))
        );
    }
}
