package com.fanta.ckservice.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.fanta.ckservice.common.QingLongException;
import com.fanta.ckservice.model.AuthInfo;
import com.fanta.ckservice.model.EnvInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;


@Component
@Slf4j
public class QlUtils {
    @Value("${ckServer.url}")
    private String GLOBAL_SERVER_URL;
    @Value("${ckServer.clientId}")
    private String CLIENT_ID;
    @Value("${ckServer.clientSecret}")
    private String CLIENT_SECRET;

    public void checkState(String rs) {
        JSONObject jsonObject = JSONUtil.parseObj(rs);
        Object message = jsonObject.get("message");
        if (message != null && message.toString().contains("SequelizeUniqueConstraintError")) {
            throw new QingLongException(400, "Cookie已存在");
        }
        String code = jsonObject.get("code").toString();
        if (!Objects.equals(code, "200")) {
            log.error("服务器异常code:{}", code);
            throw new QingLongException(500, "服务器异常，请重试");
        }
    }

    public String getToken() {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("client_id", CLIENT_ID);
        paramMap.put("client_secret", CLIENT_SECRET);
        String rs = HttpUtil.get(GLOBAL_SERVER_URL + "/auth/token", paramMap);
        checkState(rs);
        JSONObject data = JSONUtil.parseObj(rs).getJSONObject("data");
        AuthInfo authInfo = JSONUtil.toBean(data, AuthInfo.class);
        return authInfo.getToken();
    }

    public List<EnvInfo> getAllJdEnvs() {
        String authorizationValue = String.format("Bearer %s", getToken());
        String responseBody = HttpUtil.createGet(GLOBAL_SERVER_URL + "/envs?searchValue=JD_COOKIE")
                .header("authorization", authorizationValue).execute().body();
        checkState(responseBody);
        Object data = JSONUtil.toBean(responseBody, BaseResponse.class).getData();
        JSONArray jsonArray = JSONUtil.parseArray(data);
        return JSONUtil.toList(jsonArray, EnvInfo.class);
    }

    public EnvInfo addEnv(EnvInfo envInfo) {
        String authorizationValue = String.format("Bearer %s", getToken());
//        JSONObject obj = JSONUtil.createObj();
//        obj.set("name", envInfo.getName());
//        obj.set("value", envInfo.getValue());
//        obj.set("remarks", envInfo.getRemarks());
//        List<Object> list = new ArrayList<>();
//        list.add(obj);
//        String body2 = list.toString();
        String body = String.format("[{\"name\":\"%s\",\"value\":\"%s\",\"remarks\":\"%s\"}]", envInfo.getName(), envInfo.getValue(), envInfo.getRemarks());
        String responseBody = HttpUtil.createPost(GLOBAL_SERVER_URL + "/envs")
                .header("authorization", authorizationValue)
                .header("Content-Type", "application/json")
                .body(body)
                .execute().body();
        checkState(responseBody);
        Object data = JSONUtil.toBean(responseBody, BaseResponse.class).getData();
        //[{"id":1446,"value":"aa","status":0 ...}]
        JSONArray jsonArray = JSONUtil.parseArray(data);
        BeanUtil.copyProperties(jsonArray.get(0), envInfo);
        return envInfo;
    }

    public BaseResponse updateEnv(EnvInfo envInfo) {
        String authorizationValue = String.format("Bearer %s", getToken());
        JSONObject obj = JSONUtil.createObj();
        obj.set("name", envInfo.getName());
        obj.set("value", envInfo.getValue());
        obj.set("remarks", envInfo.getRemarks());
        obj.set("id", envInfo.getId());
        String body = obj.toString();
        String responseBody = HttpRequest.put(GLOBAL_SERVER_URL + "/envs")
                .header("authorization", authorizationValue)
                .header("Content-Type", "application/json")
                .body(body)
                .execute().body();
        checkState(responseBody);
        enableEnv(envInfo.getId());
        BaseResponse baseResponse = JSONUtil.toBean(responseBody, BaseResponse.class);
        return baseResponse;
    }

    public BaseResponse getEnvById(Integer id) {
        String authorizationValue = String.format("Bearer %s", getToken());
        String responseBody = HttpUtil.createGet(GLOBAL_SERVER_URL + "/envs/" + id)
                .header("authorization", authorizationValue).execute().body();
        BaseResponse baseResponse = JSONUtil.toBean(responseBody, BaseResponse.class);
        return baseResponse;
    }

    public BaseResponse enableEnv(Integer id) {
        String authorizationValue = String.format("Bearer %s", getToken());
        List<Integer> idList = new ArrayList<>();
        idList.add(id);
        String body = idList.toString();
        String responseBody = HttpRequest.put(GLOBAL_SERVER_URL + "/envs/enable")
                .header("authorization", authorizationValue)
                .body(body)
                .execute().body();
        BaseResponse baseResponse = JSONUtil.toBean(responseBody, BaseResponse.class);
        return baseResponse;
    }

    public BaseResponse disableEnv(Integer id) {
        String authorizationValue = String.format("Bearer %s", getToken());
        List<Integer> idList = new ArrayList<>();
        idList.add(id);
        String body = idList.toString();
        String responseBody = HttpRequest.put(GLOBAL_SERVER_URL + "/envs/disable")
                .header("authorization", authorizationValue)
                .body(body)
                .execute().body();
        BaseResponse baseResponse = JSONUtil.toBean(responseBody, BaseResponse.class);
        return baseResponse;
    }
}

