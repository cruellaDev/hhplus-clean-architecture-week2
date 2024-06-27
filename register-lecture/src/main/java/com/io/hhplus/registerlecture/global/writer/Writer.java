package com.io.hhplus.registerlecture.global.writer;

public interface Writer<Source, Target> {
    Target write(Source source);
}
