package com.atguigu.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("payment/gateway")
public class GatewayController {

    @Value("${server.port}")
    private String port;

    @GetMapping("gw1/test1")
    public String gatewayTest1(@RequestParam("uname") String username, @RequestHeader("X-Request-red") String requestHeader) {
        return "[gatewayTest1] ServerPort: " + port + ", X-Request-red = " + requestHeader + ", uname = " + username;
    }

    @GetMapping("gw2/test2")
    public String gatewayTest2(@RequestParam("uname") String username) {
        return "[gatewayTest2] ServerPort: " + port + ", uname = " + username;
    }

}
