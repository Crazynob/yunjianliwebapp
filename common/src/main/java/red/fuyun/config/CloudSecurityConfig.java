package red.fuyun.config;


import red.fuyun.Interceptor.CloudSecurityInterceptorConfigure;


public class CloudSecurityConfig {


//    @Bean
    public CloudSecurityInterceptorConfigure cloudSecurityInterceptorConfigure() {
        return new CloudSecurityInterceptorConfigure();
    }
}
