package com.ujisistemcassandra.controller;


import com.ujisistemcassandra.compareter.CompareList;
import com.ujisistemcassandra.converter.listToLowercase;
import com.ujisistemcassandra.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class ServiceController {

    private final ServiceRepository serviceRepository;
    @Autowired
    public ServiceController(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @GetMapping("/cassandra")
    public ResponseEntity<List<Map<String, Object>>> getAllData() {
        try {
            List<Map<String, Object>> dataList = serviceRepository.getAllData();
            return ResponseEntity.ok(dataList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/cassandra")
    public ResponseEntity<String> insertData(
            @RequestHeader HttpHeaders headers,
            @RequestBody List<Map<String, Object>> dataList
    ) {
        try {
//            String tableName = headers.getFirst("tableName");
//            System.out.println(tableName);
            List<String> key = new ArrayList<>();
            for (Map<String, Object> data : dataList) {
                key.addAll(data.keySet());
                break;
            }

            listToLowercase convToLow = new listToLowercase();
            List<String> upKey = convToLow.listLowercase(key);

            List<String> tableName = serviceRepository.getAllTableNames("ujisistemc");
            for (String table : tableName) {
                List<String> column = serviceRepository.getColumnList(table, "ujisistemc");
                boolean cmpr = CompareList.compareLists(upKey, column);
                if (cmpr) {
                    System.out.println("yes");
                } else {
                    System.out.println("no");
                }
                System.out.println(column);
                System.out.println(upKey);
//                System.out.println("\n\n");
            }


//            System.out.println(tableName);


//            String tableName = serviceRepository.createTable(key);
//            serviceRepository.insertData(dataList, tableName);
//            for (Map<String, Object> data : dataList) {
//                  serviceRepository.insertData(data);
////                System.out.println(data.keySet());
////                key.add(data.keySet().toString());
//                  key.addAll(data.keySet());
////                System.out.println(data.values());
//            }
            return ResponseEntity.ok("Data inserted succesfully!");
        } catch (Exception e) {
            String eMessage = "Failed to insert data!";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.toString());
        }
    }


}
