package com.io.hhplus.registerlecture.global.reader;

public interface Reader<D> {
    D getOneByPrimaryId(Long primaryId);
}
