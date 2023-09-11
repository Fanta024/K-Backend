package com.fanta.ckservice.controller;

import cn.hutool.core.util.StrUtil;
import com.fanta.ckservice.model.CkInfo;
import com.fanta.ckservice.model.CkUpdateRequest;
import com.fanta.ckservice.model.EnvInfo;
import com.fanta.ckservice.utils.BaseResponse;
import com.fanta.ckservice.utils.QlUtils;
import com.fanta.ckservice.utils.ResultUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestController
public class CkController {
    @Resource
    private QlUtils qlUtils;

    public String checkCk(String cookie){
        String pt_Key = "";
        Pattern pattern1 = Pattern.compile("pt_key=(.*?);");
        Matcher matcher1 = pattern1.matcher(cookie);
        if (matcher1.find()) {
            pt_Key = matcher1.group(1);
        }
        String pt_Pin = "";
        Pattern pattern2 = Pattern.compile("pt_pin=(.*?);");
        Matcher matcher2 = pattern2.matcher(cookie);
        if (matcher2.find()) {
            pt_Pin = matcher2.group(1);
        }
        if (!StrUtil.isAllNotBlank(pt_Pin, pt_Key)) {
            return null;
        }
        return String.format("pt_key=%s;pt_pin=%s;", pt_Key, pt_Pin);

    }
    @PostMapping("/add")
    public BaseResponse<EnvInfo> addUser(@RequestBody CkInfo ckInfo) {
        String cookie = ckInfo.getCookie();
        String ck = checkCk(cookie);
        if (StrUtil.isEmpty(ck)) {
            return ResultUtils.error(400, "请输入正确ck");
        }
        EnvInfo envInfo = new EnvInfo();
        envInfo.setName("JD_COOKIE");
        envInfo.setValue(ck);
        envInfo.setRemarks(ckInfo.getRemarks());
        EnvInfo addResponseEnvInfo = qlUtils.addEnv(envInfo);
        return ResultUtils.success(addResponseEnvInfo);
    }
    @PostMapping("/update")
    public BaseResponse<String> updateUser(@RequestBody CkUpdateRequest ckUpdateRequest){
        String cookie = ckUpdateRequest.getValue();
        String ck = checkCk(cookie);
        if (StrUtil.isEmpty(ck)) {
            return ResultUtils.error(400, "请输入正确ck");
        }
        EnvInfo envInfo = new EnvInfo();
        envInfo.setName("JD_COOKIE");
        envInfo.setId(ckUpdateRequest.get_id());
        envInfo.setValue(ck);
        envInfo.setRemarks(ckUpdateRequest.getRemarks());
        BaseResponse baseResponse = qlUtils.updateEnv(envInfo);
        Integer code = baseResponse.getCode();
        if(code==200){
            return ResultUtils.success();
        }
        return ResultUtils.error(400,"更新失败");
    }
    @GetMapping("/info")
    public BaseResponse<List<EnvInfo>> getUserInfo(String remarks){
        List<EnvInfo> allList= qlUtils.getAllJdEnvs();
        List<EnvInfo> collect = allList.stream().filter(item -> Objects.equals(item.getRemarks(), remarks)).collect(Collectors.toList());

        return ResultUtils.success(collect);
    }
}
