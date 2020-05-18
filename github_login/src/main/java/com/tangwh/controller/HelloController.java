package com.tangwh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Controller
public class HelloController {

    @Autowired
    RestTemplate restTemplate;
    @RequestMapping("/index.html")
    public String index(){
        return "index";
    }

    @GetMapping("/authorization_code")
    public String authorization_code(String code) {

        Map<String, String> map = new HashMap<>();
        // 客户端id
        map.put("client_id", "6e4cfe962ae26d102e2e");
        // 客户端密码
        map.put("client_secret", "ecc5d882920ef13b5e9716abf0118d6cad33dd64");
        // 登录成成功后的授权码
        map.put("code",code);
        // 状态
        map.put("state","javaboy");
        // 成功后重定向的地址
        map.put("redirest_uri","http://localhost:8080/authorization_code");

    // 返回的用户信息
        Map<String, String> resp = restTemplate.postForObject("https://github.com/login/oauth/access_token", map,
                Map.class);

        // 获取用户信息
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "token "+resp.get("access_token"));
        HttpEntity httpEntity=new HttpEntity(httpHeaders);
        ResponseEntity<Map> entity = restTemplate.exchange("https://api.github.com/user", HttpMethod.GET, httpEntity,
                Map.class);

        System.out.println("entity.getBody()="+entity.getBody());

        return "forward:/index.html";

    }


}
