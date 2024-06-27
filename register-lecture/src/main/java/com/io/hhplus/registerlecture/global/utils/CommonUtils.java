package com.io.hhplus.registerlecture.global.utils;

import com.io.hhplus.registerlecture.global.processor.ProcessCode;
import com.io.hhplus.registerlecture.global.processor.ProcessResultDto;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

public class CommonUtils {

    public static ProcessResultDto createProcessResult(ProcessCode processCode, String detailMessage) {
        return new ProcessResultDto(processCode, detailMessage);
    }

    public static boolean isEmpty(Object o) {
        if (o == null) return true;
        if (o instanceof CharSequence) return ((CharSequence) o).isEmpty();
        if (o instanceof Collection) return ((Collection<?>) o).isEmpty();
        if (o instanceof Map) return ((Map<?,?>) o).isEmpty();
        if (o.getClass().isArray()) return (Array.getLength(o) == 0);
        return false;
    }

    public static boolean hasYN(String sYn) {
        return Arrays.asList("Y", "N").contains(sYn);
    }

    public static boolean isNotFoundData(long id) {
        return id == GlobalConstants.NOT_FOUND_ID;
    }

    public static boolean isUsable(String sYn) {
        return hasYN(sYn) && sYn.equals("Y");
    }

    public static boolean isBlank(String s) {
        return isEmpty(s) || s.isBlank();
    }

    public static boolean isPositiveLong(Long lNumber) {
        return !isEmpty(lNumber) && lNumber.compareTo(0L) > 0;
    }
}
