package com.io.hhplus.registerlecture.presentation.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ListResponse<T> extends CommonResponse {
    private List<T> dataList;
    private String code;
    private String message;

    public ListResponse(Boolean scs, List<?> data, String code, String message) {
        super(scs, code, message);
    }

    public static ListResponse<?> from(Boolean scs, List<?> data, String code, String message) {
        return new ListResponse<>(scs, data, code, message);
    }
}
