package com.tangwh;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@SpringBootTest
class ClicentAppApplicationTests {
    @Autowired
    RestTemplate restTemplate;

    /**
     * 客户端登录模式
     */
    @Test
    void contextLoads() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap();

        // 客户端id
        map.add("client_id","javaboy");
        // 客户端密码
        map.add("client_secret","123");
        // 授权模式(密码模式)
        map.add("grant_type","client_credentials");


        // 参数一 要请求的接口 参数二 请求的参数 参数三 返回的格式  响应结果:令牌信息
        Map<String, String> resp = restTemplate.postForObject("http://localhost:8080/oauth/token", map, Map.class);

        System.out.println("resp="+resp);
        //拿到令牌 请求资源 参数一: 请求的接口  参数二:请求的方式 参数三 请求的实体类 参数四 返回的数据类型
        // 配置请求头信息
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","Bearer"+resp.get("access_token"));
        HttpEntity<Object> httpParams =new HttpEntity<>(headers);
        ResponseEntity<String> entity = restTemplate.exchange("http://localhost:8081/hello", HttpMethod.GET, httpParams,
                String.class);

        System.out.println(entity.getBody());




    }

}
