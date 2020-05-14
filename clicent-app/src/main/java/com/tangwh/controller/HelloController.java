package com.tangwh.controller;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.function.ServerRequest;

import java.net.URI;
import java.util.Map;
import java.util.UUID;

@Controller
public class HelloController {


   @Autowired
    RestTemplate restTemplate;

    /**
     * 顺序 自己应用 --> 授权服务器——>授权服务器验证密码 成功后-->自己应用 返回一个 授权码
     *  通过 授权码 获取到令牌 拿到令牌可以取--> 资源服务器 获取数据
     * @param code
     * @param model
     * @return
     */
    @GetMapping("/index.html")
    public String index(String code, Model model) {
     // 拿到 授权服务返回的一个 授权码
        // 接口里面再次发送请求
        if (code != null){
            // 使用这个map的原因:Post请求 如果使用普通的map 是以Json形式传递 如果是MultiValueMap 使用的key-value形式
            MultiValueMap<String,String> map = new LinkedMultiValueMap();
            map.add("code",code);
            // 客户端id 配置在 资源服务器上
            map.add("client_id","javaboy");
            // 客户端 密码 配置在 资源服务器上
            map.add("client_secret","123");
            // 重定向的地址
            map.add("redirect_uri","http://localhost:8082/index.html");
            // 授权的的类型 授权码模式
            map.add("grant_type","authorization_code");

            // 参数 1：请求地址   2：参数  3 响应的实体 返回的数据
            Map<String,String> resp = restTemplate.postForObject("http://localhost:8080/oauth/token", map, Map.class);

            // 拿到 像响应结果 里面有 令牌  还有刷新的令牌 等等 ---
            System.out.println("resp="+resp);
            // 拿到令牌 可以 向资源服务器请求数据  令牌需要放在请求头里面

            // 定义请求头
            HttpHeaders headers = new HttpHeaders();
            // 添加头的key ,Value
            headers.add("Authorization","Bearer"+resp.get("access_token"));

             HttpEntity<?> httEntity = new HttpEntity<>(headers);
            // 参数 1：请求的接口(请求资源服务器上的接口) 2 请求方式  3 重新定义的请求头  4 响应数据类型 返回数据
            ResponseEntity<String> responseEntity = restTemplate.exchange("http://localhost:8081/hello", HttpMethod.GET, httEntity, String.class);

            // 获取用户数据
            model.addAttribute("msg", responseEntity.getBody());

        }

        return "index";


    }


}
