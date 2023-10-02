package com.business.brendaapp.payloads.out;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ResponseFileService {
    private int code;
    private String message;
    private Object value;
}
