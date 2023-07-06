package com.ujisistemcassandra;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class coba {
    public static void main(String[] args) {
//        String inputDate = "20020902";
//        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyyMMdd");
//        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
//
//        try {
//            Date date = inputFormat.parse(inputDate);
//            String outputDate = outputFormat.format(date);
//            System.out.println(outputDate);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

        Map<String, Object> dataMap = new HashMap<>();
        Map<String, Object> teacher = new HashMap<>();
        Map<String, Object> student = new HashMap<>();

        teacher.put("name", "fandi");
        teacher.put("age", "11");
        teacher.put("city", "Salatiga");

        student.put("name", "Budi");
        student.put("School", "UKSW");
        student.put("city", "Salatiga");

        dataMap.put("teacher", teacher);
        dataMap.put("student", student);

        System.out.println(dataMap);
    }
}
