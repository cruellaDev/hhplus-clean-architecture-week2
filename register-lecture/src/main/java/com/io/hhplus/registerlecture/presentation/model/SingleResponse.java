package com.io.hhplus.registerlecture.presentation.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SingleResponse<T> extends CommonResponse {
    private T data;
    private String code;
    private String message;

    public SingleResponse(Boolean scs, Object data, String code, String message) {
        super(scs, code, message);
    }

    public static SingleResponse<?> from(Boolean scs, Object data, String code, String message) {
        return new SingleResponse<>(scs, data, code, message);
    }
}
