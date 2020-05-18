package com.tangwh;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Base64;

@SpringBootTest
class AuthServerJwtApplicationTests {

    /**
     * jwt格式
     * "access_token":
     * 1: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.
     * 2:eyJhdWQiOlsicmVzMSJdLCJ1c2VyX25hbWUiOiJqYXZhYm95Iiwic2NvcGUiOlsiYWxsIl0sImV4cCI6MTU4OTgwMTQ1MSwiYXV0aG9yaXRpZXMiOlsiUk9MRV91c2VyIl0sImp0aSI6IjQ0YzUzOTFjLTFhMjQtNGIxNi05NTQwLTVmNTdjZjljMzM5ZiIsImNsaWVudF9pZCI6ImphdmFib3kifQ.
     * 3:6w7eoinuQxe3BdKRW8lNMu6lg1oVlG8htf2eMKUyUtw
     */
    @Test
    void contextLoads() {

      String s = new String(Base64.getDecoder().decode("eyJhdWQiOlsicmVzMSJdLCJ1c2VyX25hbWUiOiJqYXZhYm95Iiwic2NvcGUiOlsiYWxsIl0sImV4cCI6MTU4OTgwMTQ1MSwiYXV0aG9yaXRpZXMiOlsiUk9MRV91c2VyIl0sImp0aSI6IjQ0YzUzOTFjLTFhMjQtNGIxNi05NTQwLTVmNTdjZjljMzM5ZiIsImNsaWVudF9pZCI6ImphdmFib3kifQ"));
        System.out.println(s);
    }

}
