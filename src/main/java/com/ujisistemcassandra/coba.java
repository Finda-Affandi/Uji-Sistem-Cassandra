package com.ujisistemcassandra;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class coba {
    public static void main(String[] args) {
        String inputDate = "20020902";
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date = inputFormat.parse(inputDate);
            String outputDate = outputFormat.format(date);
            System.out.println(outputDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
