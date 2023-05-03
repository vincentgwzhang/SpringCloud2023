package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.dto.CommonResult;
import com.atguigu.springcloud.dto.PaymentDTO;
import com.atguigu.springcloud.service.PaymentFeignService;
import feign.RetryableException;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class OrderFeignController
{
    @Resource
    private PaymentFeignService paymentFeignService;

    @GetMapping(value = "/consumer/payment/{id}")
    public CommonResult<PaymentDTO> getPaymentById(@PathVariable("id") Long id)
    {
        return paymentFeignService.getPaymentById(id);
    }

    @GetMapping(value = "/consumer/error/")
    public String paymentFeignTimeout()
    {
        // OpenFeign客户端一般默认等待1秒钟
        String result;
        try
        {
            paymentFeignService.getResponseError("time_over");
            return "success";
        }
        catch (RetryableException e)
        {
            result = e.getMessage();
        }
        return result;
    }
}