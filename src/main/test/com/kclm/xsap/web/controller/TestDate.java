package com.kclm.xsap.web.controller;

import java.time.*;
import java.util.Date;

/**
 * @author fangkai
 * @description
 * @create 2021-12-27 12:42
 */
public class TestDate {
    public static LocalDate toLocalDate(Date date) {
        //获取传入的date的瞬时： 表示从1970年**月**日到现在的毫秒数
        Instant instant = date.toInstant();
        //获取传入的Date的时区
        ZoneId zoneId = ZoneId.systemDefault();
        //通过LocalDateTime的静态方法构造一个指定时区下的瞬时数的localdatetime
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zoneId);
        // 将LocalDateTIme转化为LocalDate 并返回
        return localDateTime.toLocalDate();

    }

    public static Date toDate(LocalDate localDate) {

        //获取当前时区
        ZoneId zoneId = ZoneId.systemDefault();
        //将传入的localDate转化 为瞬时
        Instant instant = localDate.atStartOfDay().atZone(zoneId).toInstant();

        return Date.from(instant);

    }
    public static void main(String[] args) {
        Date date = new Date();
        LocalDate toLocalDate = toLocalDate(date);
        System.out.println("生成一个date==>" + date + "\n转化为LocalDate后==>" + toLocalDate + "\n");

        LocalDate now = LocalDate.now();
        Date toDate = toDate(now);
        System.out.println("生成一个LocalDate==>" + now + "\n转化为Date后==>" + toDate);

    }

}
