package red.fuyun.Interceptor.feign;


import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.codec.Base64;

import java.io.UnsupportedEncodingException;


public class FeignRequestInterceptor implements RequestInterceptor {

    public void apply(RequestTemplate requestTemplate) {
        String authorization = "";
        try {
            authorization =  String.format("Basic %s", new String(Base64.encode(String.format("%s:%s", "login", "pass").getBytes("UTF-8")), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        requestTemplate.header(HttpHeaders.AUTHORIZATION, authorization);
    }
}
