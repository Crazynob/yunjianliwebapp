package red.fuyun.filter;

import com.alibaba.fastjson.JSONObject;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACVerifier;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;


@Component
@Order(0)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PermissionCheckFilter implements GlobalFilter {

    private final RedisTemplate<String,String> redisTemplate;

    @SneakyThrows
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        RequestPath requestPath = request.getPath();
        URI uri = request.getURI();

        if (requestPath.toString().equals("/user/login")){
            return chain.filter(exchange);
        }


        HttpHeaders headers = request.getHeaders();
        String token = headers.getFirst("X-Token");
        JWSObject jwsObject = JWSObject.parse(token);
        // 判断当前令牌是否在黑名单中
        if(!StringUtils.isEmpty(token) && isBlack(jwsObject) && isInvalid(jwsObject)){
            throw new HttpServerErrorException(HttpStatus.FORBIDDEN,"登录超时请重新登录!");
        }

        isPermission(jwsObject);

        return chain.filter(exchange);
    }

    private boolean isPermission(JWSObject jwsObject) {
        Payload payload = jwsObject.getPayload();
        Map<String, Object> stringObjectMap = payload.toJSONObject();
        
        return true;
    }


    /**
     * 通过redis判断token是否为黑名单
     * @param jwsObject 请求头
     * @return boolean
     */
    private boolean isBlack(JWSObject jwsObject) throws ParseException, JOSEException {

        //todo  移除所有oauth2相关代码，暂时使用 OAuth2AccessToken.BEARER_TYPE 代替
//        String token  = headerToken.replace("Bearer", "").trim();

        //解析token
//        JWSObject jwsObject = JWSObject.parse(token);
//        {"aud":["all_login"],"user_name":"fuyun","scope":["all"],"exp":1641193438,"authorities":["p1"],"jti":"c289f8cb-d5c2-4e08-bfc4-debd2e29066a","client_id":"login"}
        String payload = jwsObject.getPayload().toString();
        JSONObject payloadObject = JSONObject.parseObject(payload);
//        isInvalid(headerToken);
        // JWT唯一标识
        String jti = payloadObject.getString("jti");
//        redisTemplate.boundValueOps(jti).set(jti);
        Boolean hasKey = redisTemplate.hasKey(jti);
        return hasKey;
    }


    private boolean isInvalid(JWSObject jwsObject) throws ParseException, JOSEException {

        //从token中解析JWS对象
//        JWSObject jwsObject = JWSObject.parse(token);
        //创建HMAC验证器
        JWSVerifier jwsVerifier = new MACVerifier("");
        if (!jwsObject.verify(jwsVerifier)) {
//            throw new Exception("token签名不合法！");
            return false;
        }
        String payload = jwsObject.getPayload().toString();
        JSONObject payloadObject = JSONObject.parseObject(payload);
        Long exp = payloadObject.getLong("exp");
        if (exp< new Date().getTime()) {
            return false;
        }
        return true;

    }

    
}
