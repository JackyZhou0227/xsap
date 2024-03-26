package com.kclm.xsap.utils.time;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Data {

    public static List<Integer> Data(List<LocalDateTime> timeList,List<String> dayList){
        int day= LocalDate.now().getDayOfMonth();
        int year=LocalDate.now().getYear();
        int month=LocalDate.now().getMonthValue();
        List<Integer> data=new ArrayList<>();
         for (String s : dayList) {
            int count=0;
            for (LocalDateTime localDateTime : timeList) {
                if (localDateTime.getYear() == year
                        &&localDateTime.getMonthValue() ==month
                        && String.valueOf(localDateTime.getDayOfMonth()).equals(s)) {
                    ++count;
                    System.out.println("----------"+count);
                }
            }
            data.add(count);
        }
        return data;
    }
}
