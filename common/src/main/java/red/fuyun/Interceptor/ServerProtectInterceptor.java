package red.fuyun.Interceptor;

import org.springframework.lang.NonNull;
import org.springframework.util.Base64Utils;
import org.springframework.web.servlet.HandlerInterceptor;
import red.fuyun.model.CloudConstant;
import red.fuyun.model.R;
import red.fuyun.properties.CloudSecurityProperties;
import red.fuyun.util.JsonUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class ServerProtectInterceptor implements HandlerInterceptor {

    private CloudSecurityProperties properties;

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler){

        if (!properties.getOnlyFetchByGateway()) {
            return true;
        }

        String token = request.getHeader(CloudConstant.GATEWAY_TOKEN_HEADER);

        String gatewayToken = new String(Base64Utils.encode(CloudConstant.GATEWAY_TOKEN_VALUE.getBytes()));

        if (gatewayToken.equals(token)) {
            return true;
        } else {

            R<Object> resultData = R.fail(HttpServletResponse.SC_FORBIDDEN, "请通过网关访问资源");
//            WebUtils.writeJson(response,resultData);
            String result = JsonUtils.obj2string(resultData);
            try {
//                response.setStatus(500);
                response.setCharacterEncoding("UTF-8");
                response.addHeader("Content-Type","application/json; charset=utf-8");
                response.getWriter().print(result);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;

        }
    }

    public void setProperties(CloudSecurityProperties properties) {
        this.properties = properties;
    }
}
