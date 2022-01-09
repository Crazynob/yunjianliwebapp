package red.fuyun.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import red.fuyun.service.UserService;

import javax.sql.DataSource;


//http://127.0.0.1:8080/oauth/authorize?response_type=code&client_id=login&redirect_uri=https://www.baidu.com&scope=all
//http://127.0.0.1:8080/oauth/authorize?response_type=token&client_id=login&redirect_uri=http://www.baidu.com&scope=all
//http://127.0.0.1:8080//oauth/token?username=fuyun&password=123&grant_type=password&scope=all&client_id=admin
@Configuration
@EnableAuthorizationServer


public class AuthoriztionServerConfig  extends AuthorizationServerConfigurerAdapter {

    public final String SIGNINGKEY = "SigningKey";

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;//密码模式需要注入认证管理器

    @Autowired
    UserService userService;

    @Autowired
    DataSource dataSource;




    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.checkTokenAccess("permitAll()");
        security.allowFormAuthenticationForClients();
        security.addTokenEndpointAuthenticationFilter(new CHandlerInterceptor());

    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        inMemory(clients);
        jdbc(clients);

    }

    private void jdbc(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.jdbc(dataSource);
        clients.withClientDetails(jdbcClientDetailsService());
    }


    private void inMemory(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("login")
                .secret(passwordEncoder.encode("pass"))
                .authorizedGrantTypes("authorization_code","password","implicit","client_credentials")
                .scopes("all")
                .resourceIds("all_login")
                .redirectUris("https://www.baidu.com");

    
    }

    @Bean
    public ClientDetailsService jdbcClientDetailsService() {
        JdbcClientDetailsService jdbcClientDetailsService = new JdbcClientDetailsService(dataSource);
        return jdbcClientDetailsService;

    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager);
        endpoints.userDetailsService(userService);
        endpoints.tokenStore(tokenStore());
        endpoints.tokenServices(tokenService());
        endpoints.tokenEnhancer(jwtAccessTokenConverter());
    }




    @Bean
    public AuthorizationServerTokenServices tokenService() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setAuthenticationManager(authenticationManager);
        defaultTokenServices.setTokenEnhancer(jwtAccessTokenConverter());
        defaultTokenServices.setTokenStore(tokenStore());
        //此处可以自定义ClientDetailsService 逻辑,只是调用loadClientByClientId方法 获取ClientDetails 若是没有逻辑可以不写
//        InMemoryClientDetailsService inMemoryClientDetailsService = new InMemoryClientDetailsService();

        defaultTokenServices.setClientDetailsService(jdbcClientDetailsService());
        //设置token有效期 单位S
        defaultTokenServices.setAccessTokenValiditySeconds(12*60);

        return defaultTokenServices;
    }

    @Bean
    public TokenStore tokenStore() {
        return  new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
//        jwtAccessTokenConverter.setAccessTokenConverter(null);
        jwtAccessTokenConverter.setSigningKey(SIGNINGKEY);
        return jwtAccessTokenConverter;
    }
}
