package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.dto.CommonResult;
import com.atguigu.springcloud.dto.PaymentDTO;
import com.atguigu.springcloud.service.PaymentService;
import jakarta.validation.Valid;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("feign-client")
@Validated
public class OpenFeignServerController {

    private final PaymentService paymentService;

    @Autowired
    public OpenFeignServerController(final PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping(value = "{id}")
    @ResponseStatus(HttpStatus.OK)
    public CommonResult<PaymentDTO> getPayment(@PathVariable Long id) {
        PaymentDTO result = paymentService.getPaymentById(id);
        return new CommonResult<>(HttpStatus.OK.value(), "Get payment id success", result);
    }

    @GetMapping(value = "error/{errorType}")
    @ResponseStatus(HttpStatus.OK)
    public CommonResult<String> getResponseError(@PathVariable String errorType) {
        if (StringUtils.equals(errorType, "time_over")) {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return new CommonResult<>(HttpStatus.OK.value(), "Finally get result but over time", null);
        } else {
            return new CommonResult<>(HttpStatus.NOT_ACCEPTABLE.value(), "Get result without correct", null);
        }
    }

    @DeleteMapping(value = "{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public CommonResult<String> deletePayment(@PathVariable Long id) {
        paymentService.deletePaymentById(id);
        return new CommonResult<>(HttpStatus.ACCEPTED.value(), "delete payment success", null);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommonResult<PaymentDTO> createPayment(@Valid @RequestBody PaymentDTO paymentDTO) {
        PaymentDTO result = paymentService.createPayment(paymentDTO);
        return new CommonResult<>(HttpStatus.CREATED.value(), "Create payment success", result);
    }

}
