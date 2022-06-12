package com.ecommerce.computer.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuccessResponse {
    private final String state = "SUCCESS";
    private Object data;
}
