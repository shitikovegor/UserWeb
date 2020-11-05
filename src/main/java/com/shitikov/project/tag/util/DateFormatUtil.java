package com.shitikov.project.tag.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateFormatUtil {
    private DateFormatUtil() {
    }

    public static String formatDate(long date, boolean input) {
        LocalDate localDate = Instant.ofEpochMilli(date).atZone(ZoneId.systemDefault()).toLocalDate();
        DateTimeFormatter formatterOut = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        DateTimeFormatter formatterIn = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateFormatted = input ? localDate.format(formatterIn) : localDate.format(formatterOut);

        return dateFormatted;
    }

    public static String formatDate(long date) {
        LocalDate localDate = Instant.ofEpochMilli(date).atZone(ZoneId.systemDefault()).toLocalDate();
        DateTimeFormatter formatterOut = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        return localDate.format(formatterOut);
    }
}
