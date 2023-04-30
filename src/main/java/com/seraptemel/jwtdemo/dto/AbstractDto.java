package com.seraptemel.jwtdemo.dto;

import lombok.Data;

@Data
public abstract class AbstractDto {
    private boolean success = false;
    private String errorMessage;
}
