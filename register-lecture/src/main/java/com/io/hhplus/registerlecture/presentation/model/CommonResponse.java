package com.io.hhplus.registerlecture.presentation.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonResponse {
    private Boolean scs;
    private String code;
    private String message;

    private CommonResponse() {}

    public CommonResponse(Boolean scs, String code, String message) {
        this.scs = scs;
        this.code = code;
        this.message = message;
    }

    public static CommonResponse from(Boolean scs, String code, String message) {
        return new CommonResponse(scs, code, message);
    }
}
