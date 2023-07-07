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

//    @GetMapping("/cassandra")
//    public ResponseEntity<List<Map<String, Object>>> getAllData(@RequestHeader HttpHeaders headers) {
//        try {
//            String tableName = headers.getFirst("table-name");
//            List<Map<String, Object>> dataList = serviceRepository.getAllData(tableName);
//            return ResponseEntity.ok(dataList);
//        } catch (Exception e) {
//            String eMessage = "An error while retrieving data";
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }

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

//    @PostMapping("/cassandra")
//    public ResponseEntity<String> insertData(
//            @RequestHeader HttpHeaders headers,
//            @RequestBody List<Map<String, Object>> dataList) {
//        try {
//
//            List<String> key = new ArrayList<>();
//            for (Map<String, Object> data : dataList) {
//                key.addAll(data.keySet());
//                break;
//            }
//            //Change column name from csv into lowercase
//            listToLowercase convToLow = new listToLowercase();
//            List<String> lowKey = convToLow.listLowercase(key);
//
//            //Get all table from DB
//            List<String> tableName = serviceRepository.getAllTableNames();
//
//            //List for get compare result
//            List<Boolean> isDuplicate = new ArrayList<>();
//            for (String table : tableName) {
//                List<String> column = convToLow.listLowercase(serviceRepository.getColumnList(table));
//                boolean cmpr = CompareList.compareLists(lowKey, column);
//                if (cmpr) {
//                    isDuplicate.add(true);
//                } else {
//                    isDuplicate.add(false);
//                }
//            }
//
//            //Check is column in database dublicate
//            boolean createTable = !isDuplicate.contains(true);
//
//            //Get table name from request header (Based csv filename)
//            String newTableName = headers.getFirst("table-name");
//            System.out.println(newTableName);
//
//            //Create new table if table column name is not duplicate, and insert data into exsisting table if table column name duplicate
//            if (createTable) {
//                MapReader mapReader = new MapReader();
//                String fileName = mapReader.cmprMapper(lowKey);
//                if (fileName != null) {
//                    List<String> columnList = mapReader.mapping(fileName);
//                    serviceRepository.createTableWithMap(columnList, newTableName);
//
//                    Map<String, Object> dataType = mapReader.intOrStr(fileName);
//                    serviceRepository.insertDataWithMap(dataList, dataType, newTableName);
//                } else {
//                    serviceRepository.createTable(key, newTableName);
//                    serviceRepository.insertData(dataList, newTableName);
//                }
//            } else {
//                MapReader mapReader = new MapReader();
//                String fileName = mapReader.cmprMapper(lowKey);
//                if (fileName != null) {
//                    List<String> columnList = mapReader.mapping(fileName);
//                    Map<String, Object> dataType = mapReader.intOrStr(fileName);
//                    serviceRepository.insertDataWithMap(dataList, dataType, newTableName);
//                } else {
//                    serviceRepository.createTable(key, newTableName);
//                    serviceRepository.insertData(dataList, newTableName);
//                }
//            }
//            return ResponseEntity.ok("Data inserted succesfully!");
//        } catch (Exception e) {
//            String eMessage = "Failed to insert data!";
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(eMessage);
//        }
//    }

    @PostMapping("/cassandra")
    public ResponseEntity<String> insertDAata(
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
        return ResponseEntity.ok(table + " Waktu : " + duration + "ms");
    } catch (Exception e) {
        String eMessage = "Failed to insert data!";
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(eMessage);
    }
    }

    @PostMapping("/cassandra/create-table")
    public ResponseEntity<String> createTable(
            @RequestHeader HttpHeaders headers,
            @RequestBody Map<String, Object> dataColumn)
    {
        String tableName = headers.getFirst("table-name");
        long startTime = System.currentTimeMillis(); // Waktu mulai
        serviceRepository.createTable(dataColumn, tableName);
        dataTypeMapping.put(tableName, dataColumn);
        long endTime = System.currentTimeMillis(); // Waktu selesai
        long duration = endTime - startTime; // Durasi akses (dalam milidetik)
        return ResponseEntity.ok("Waktu : " + duration + "ms");
    }

    @PostMapping("/coba")
    public void cobah(@RequestBody List<Map<String, Object>> dataList){
        serviceRepository.mencoba(dataList);
    }
}

