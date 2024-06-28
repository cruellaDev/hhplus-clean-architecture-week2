package com.io.hhplus.registerlecture.global.processor;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProcessResultDto {
    private ProcessCode processCode;
    private String detailMessage;

    public ProcessResultDto() {}

    public ProcessResultDto(ProcessCode processCode, String detailMessage) {
        this.processCode = processCode;
        this.detailMessage = detailMessage;
    }
}
