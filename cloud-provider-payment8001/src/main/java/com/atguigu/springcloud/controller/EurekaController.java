package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.dto.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * From Spring cloud version 2022.0.0, for all the eureka service, these two endpoints are mandatory, and in application.yml, also need to set
 *
 *     就是说如果要被调用，就一定要配置这样的 Controller
 *
 *     status-page-url: /info
 *     health-check-url: /health
 *
 */
@RestController
public class EurekaController {

    @GetMapping("info")
    @ResponseStatus(HttpStatus.OK)
    public CommonResult<String> eurekaInfo() {
        return new CommonResult<>(HttpStatus.OK.value(), "Info :: OK", "Success");
    }

    @GetMapping("health")
    @ResponseStatus(HttpStatus.OK)
    public CommonResult<String> eurekaHealth() {
        return new CommonResult<>(HttpStatus.OK.value(), "Health :: OK", "Success");
    }

}
