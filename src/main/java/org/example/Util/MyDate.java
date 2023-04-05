package org.example.Util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class MyDate {
    public static Date toDate(LocalDate localDate){
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return date;
    }

}
