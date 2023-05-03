package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.dto.CommonResult;
import com.atguigu.springcloud.dto.PaymentDTO;
import com.atguigu.springcloud.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("payment")
@Validated
public class PaymentController {

    private final PaymentService paymentService;

    private final DiscoveryClient discoveryClient;

    @Value("${server.port}")
    private String port;

    @Autowired
    public PaymentController(PaymentService paymentService, DiscoveryClient discoveryClient) {
        this.paymentService = paymentService;
        this.discoveryClient = discoveryClient;
    }

    @PostMapping(value = "create")
    @ResponseStatus(HttpStatus.CREATED)
    public CommonResult<PaymentDTO> createPayment(@Valid @RequestBody PaymentDTO paymentDTO) {
        PaymentDTO result = paymentService.createPayment(paymentDTO);
        return new CommonResult<>(HttpStatus.CREATED.value(), "Create Success, port = " + port, result);
    }

    @GetMapping(value = "{id}")
    public CommonResult<PaymentDTO> getPaymentById(@PathVariable("id") Long id) {
        PaymentDTO result = paymentService.getPaymentById(id);
        if (Objects.nonNull(result)) {
            return new CommonResult<>(HttpStatus.OK.value(), "Payment retrieve success, port = " + port, result);
        } else {
            return new CommonResult<>(HttpStatus.NOT_FOUND.value(), "Payment not found, port = " + port);
        }
    }

    @GetMapping(value = "/discovery")
    public Object discovery() {
        List<String> services = discoveryClient.getServices();//获取所有注册过的服务
        System.out.println("=================================================");
        System.out.println("Part 1: =========================================");
        for (String service : services) {
            System.out.println("**** Service = " + service);
        }
        System.out.println("Part 2: =========================================");
        List<ServiceInstance> list = discoveryClient.getInstances("cloud-payment-service".toUpperCase());
        System.out.println("=========================================");
        for (ServiceInstance serviceInstance : list) {
            System.out.println(serviceInstance.getInstanceId() + "\t" + serviceInstance.getHost() + "\t" + serviceInstance.getPort() + "\t" + serviceInstance.getUri());
        }
        System.out.println("=================================================");
        return this.discoveryClient;
    }
}
