package com.io.hhplus.registerlecture.global.utils;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DateUtilsTest {

    /**
     * DateUtils 의 getSysDate 확인
     */
    @Test
    void test_getSysDate() {
        // given
        LocalDateTime now = LocalDateTime.now();

        // when
        Date sysDate = DateUtils.getSysDate();
        Calendar cal = Calendar.getInstance();
        cal.setTime(sysDate);

        // then
        // year
        assertEquals(now.getYear(), cal.get(Calendar.YEAR));
        // month
        assertEquals(now.getMonth().getValue(), cal.get(Calendar.MONTH) + 1);
        // date
        assertEquals(now.getDayOfMonth(), cal.get(Calendar.DATE));
        // toString
        String parsePattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat format = new SimpleDateFormat(parsePattern);
        assertEquals(now.format(DateTimeFormatter.ofPattern(parsePattern)), format.format(sysDate));
    }

    /**
     * DateUtils 의 createTemporalDateByIntParameters 확인
     */
    @Test
    void test_createTemporalDateByIntParameters() throws ParseException {
        // given
        String dateStr = "2024-02-01 11:00:05";
        String parsePattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat format = new SimpleDateFormat(parsePattern);
        Date date = format.parse(dateStr);

        // when
        Date temporalDate = DateUtils.createTemporalDateByIntParameters(2024, 2, 1, 11, 0, 5);
        // then
        assertEquals(date, temporalDate);
    }
}