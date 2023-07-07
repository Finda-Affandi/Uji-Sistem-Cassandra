package com.ujisistemcassandra.controller;


import com.ujisistemcassandra.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class ServiceController {
    private final ServiceRepository serviceRepository;

    @Autowired
    public ServiceController(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    Map<String, Object> dataTypeMapping = new HashMap<>();


    @GetMapping("/cassandra")
    public ResponseEntity<String> getAllDataforService(@RequestHeader HttpHeaders headers) {
        try {
            List<String> tableNames = serviceRepository.getAllTableNames();
            long startTime = System.currentTimeMillis(); // Waktu mulai
            List<List<Map<String, Object>>> dataLists = serviceRepository.getBothData(tableNames);
            long endTime = System.currentTimeMillis(); // Waktu selesai
            long duration = endTime - startTime; // Durasi akses (dalam milidetik)
            String waktu = duration + "ms";

            List<Map<String, Object>> combinedData = new ArrayList<>();
            for (List<Map<String, Object>> dataList : dataLists) {
                combinedData.addAll(dataList);
            }

            return ResponseEntity.ok(combinedData + "\n" + waktu);
        } catch (Exception e) {
            String eMessage = "An error occurred while retrieving data";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PostMapping("/cassandra")
    public ResponseEntity<Map<String, Object>> insertDAata(
        @RequestHeader HttpHeaders headers,
        @RequestBody List<Map<String, Object>> dataList
    ) {
    try {
        String table = headers.getFirst("table-name");
        Map<String, Object> dataMap = (Map<String, Object>) dataTypeMapping.get(table);
        long startTime = System.currentTimeMillis(); // Waktu mulai
        serviceRepository.insertData(dataList, dataMap, table);
        long endTime = System.currentTimeMillis(); // Waktu selesai
        long duration = endTime - startTime; // Durasi akses (dalam milidetik)
        Map<String, Object> response = new HashMap<>();
        response.put("table", table);
        response.put("duration", duration);
        response.put("row", dataList.size());

        return ResponseEntity.ok(response);
    }catch (Exception e) {
        String eMessage = "Failed to insert data!";
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", eMessage);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorResponse);
    }
    }

    @PostMapping("/cassandra/create-table")
    public ResponseEntity<Map<String, Object>> createTable(
            @RequestHeader HttpHeaders headers,
            @RequestBody Map<String, Object> dataColumn)
    {
        String tableName = headers.getFirst("table-name");
        long startTime = System.currentTimeMillis(); // Waktu mulai
        serviceRepository.createTable(dataColumn, tableName);
        dataTypeMapping.put(tableName, dataColumn);
        long endTime = System.currentTimeMillis(); // Waktu selesai
        long duration = endTime - startTime; // Durasi akses (dalam milidetik)
        Map<String, Object> response = new HashMap<>();
        response.put("table", tableName);
        response.put("duration", duration);
        response.put("row", dataColumn.size());

        return ResponseEntity.ok(response);
    }

}

