package com.example.demo.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorMessage {
    private int http_code;
    private int error_code;
    private String message;
}
