package com.example.colldesign.auth.config;

import com.example.colldesign.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

/**
 * 授权服务器配置
 */
@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;


    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        /*
         * 这个客户端代表着访问我们授权服务器的第三方应用程序
         * */

        //指定令牌的存储策略为redi或inMemory内存
        clients.inMemory()
                //客户端id
                .withClient("client")
                //秘钥
                .secret(passwordEncoder.encode("ihyx"))
                //重定向地址
                // .redirectUris("http://www.baidu.com")
                //.redirectUris("http://localhost:8081/login", "http://localhost:8082/login", "http://localhost:8083/login") //sso单点登录系统演示
                .redirectUris("http://localhost:8080/login") //sso单点登录系统演示
                //授权范围
                .scopes("all")
                //设置访问令牌失效时间
                .accessTokenValiditySeconds(600)
                //设置刷新令牌失效时间
                //.refreshTokenValiditySeconds(1200)
                //自动授权
                .autoApprove(false)
                /*
                 * 授权类型
                 * authorization_code：授权码类型
                 * password:密码类型
                 * refresh_token:刷新令牌
                 *
                 * */
                .authorizedGrantTypes("authorization_code", "password", "refresh_token");

    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userService)
                //指定令牌存储策略
                //.tokenStore(tokenStore)
                //使用jwt令牌存储策略
                .tokenStore(tokenStore)
                //accessToken转成jwtToken
                .accessTokenConverter(jwtAccessTokenConverter);

    }

    //SSO单点登录系统额外配置
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        //获取秘钥必须要身份验证  单点登录必须要配置
        security.tokenKeyAccess("isAuthenticated()");
    }
}

