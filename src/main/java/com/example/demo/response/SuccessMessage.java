package com.example.demo.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SuccessMessage {
    private int http_code = 200;
    private String message = "Success";
    private Object data;
    private int totalRecord = 0;
    
    SuccessMessage(Object data){
    	this.data = data;
    }
    
    SuccessMessage(Object data, int total){
    	this.data = data;
    	this.totalRecord = total;
    }
}
