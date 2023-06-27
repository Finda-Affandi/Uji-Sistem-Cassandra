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
            List<String> key = new ArrayList<>();
            for (Map<String, Object> data : dataList) {
                key.addAll(data.keySet());
                break;
            }

            listToLowercase convToLow = new listToLowercase();
            List<String> lowKey = convToLow.listLowercase(key);

            List<String> tableName = serviceRepository.getAllTableNames("ujisistemc");

            List<Boolean> isDuplicate = new ArrayList<>();
            for (String table : tableName) {
                List<String> column = convToLow.listLowercase(serviceRepository.getColumnList(table, "ujisistemc"));
                boolean cmpr = CompareList.compareLists(lowKey, column);
                if (cmpr) {
                    isDuplicate.add(true);
                } else {
                    isDuplicate.add(false);
                }
//                System.out.println(column);
//                System.out.println(lowKey);
//                System.out.println("\n\n");
            }

            boolean createTable = !isDuplicate.contains(true);

            String newTableName = headers.getFirst("tableName");

            if (createTable) {
                String table = serviceRepository.createTable(key, newTableName);
                serviceRepository.insertData(dataList, table);
            } else {
                serviceRepository.insertData(dataList, newTableName);
            }
            return ResponseEntity.ok("Data inserted succesfully!");
        } catch (Exception e) {
            String eMessage = "Failed to insert data!";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(eMessage);
        }
    }


}
