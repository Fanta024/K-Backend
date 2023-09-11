package com.fanta.ckservice.model;

import lombok.Data;

@Data
public class CkUpdateRequest {
    private Integer _id;
    private String value;
    private String remarks;
}
