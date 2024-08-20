package com.example.capstine_2.Api;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiException extends RuntimeException{
    public ApiException(String message){
        super(message);
    }
}
