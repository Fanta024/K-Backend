package com.fanta.ckservice.model;

import lombok.Data;

@Data
public class AuthInfo {
    private String token;
    private String token_type;
    private Integer expiration;
}
