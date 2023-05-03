package com.atguigu.springcloud.dto;

import com.atguigu.springcloud.validation.SerialConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {

    private Long id;

    @NotNull(message = "can not be null")
    @NotBlank(message = "can not be blank")
    @SerialConstraint(message = "Error serial value")
    private String serial;

}
