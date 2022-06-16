package com.example.colldesign.auth.controller;

import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;

@RestController
@RequestMapping("/user")
public class UserController {

    /*
     * 获取当前用户信息
     * */
    @RequestMapping("/getCurrentUser")
    public Object getCurrentUser(Authentication authentication, HttpServletRequest request) {
        //return authentication.getPrincipal();
        String header = request.getHeader("Authorization");
        //获取jwtToken
        String token = header.substring(header.lastIndexOf("bearer") + 7);
        //解析JwtToken
        Object ihyx = Jwts.parser().
                //设置秘钥
                        setSigningKey("ihyx".getBytes(Charset.forName("utf8")))
                .parse(token).getBody();
        return ihyx;
    }
}
