package com.io.hhplus.registerlecture.global.processor;

public interface Processor<D> {
    D empty();
    ProcessResultDto validateAvailableDataByPrimaryId(Long primaryId);
    ProcessResultDto validateBeforePersist(D d);
    ProcessResultDto validateAfterPersist(D d);
}
