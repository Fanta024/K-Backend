package com.fanta.ckservice.model;

import lombok.Data;


@Data
public class EnvInfo {
    private Integer id;
    private String value;
    private String timestamp;
    private Integer status;
    private Double position;
    private String name;
    private String remarks;
    private String createdAt;
    private String updatedAt;
}
