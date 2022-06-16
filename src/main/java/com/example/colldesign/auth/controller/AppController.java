package com.example.colldesign.auth.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app")
public class AppController {

    @RequestMapping("/getUserInfo")
    public Object getUserInfo(Authentication authentication) {
        return authentication.getPrincipal();
    }

}
