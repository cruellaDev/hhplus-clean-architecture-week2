package com.io.hhplus.registerlecture.global.mapper;

import java.util.List;
import java.util.Optional;

public interface EntityMapper<D, E> {
    D toDto(E e);

    E toEntity(D d);

    List<D> toDto(List<E> eList);

    List<E> toEntity(List<D> dList);

    Optional<D> toDto(Optional<E> e);

    Optional<E> toEntity(Optional<D> d);
}
