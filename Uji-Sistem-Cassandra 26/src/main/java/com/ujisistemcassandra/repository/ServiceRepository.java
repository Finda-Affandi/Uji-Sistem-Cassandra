package com.ujisistemcassandra.repository;

import com.datastax.oss.driver.api.core.CqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSetMetaData;
import java.util.*;

@Repository
public class ServiceRepository {
    private final JdbcTemplate jdbcTemplate;

    public ServiceRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public List<Map<String, Object>> getAllData(String tableName) {
        String sql = String.format("SELECT * FROM %s", tableName);
        return jdbcTemplate.query(sql, (resultSet) -> {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            List<Map<String, Object>> dataList = new ArrayList<>();
            while (resultSet.next()) {
                Map<String, Object> dataMap = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    Object columnValue = resultSet.getObject(i);
                    dataMap.put(columnName, columnValue);
                }
                dataList.add(dataMap);
            }
            return dataList;
        });
    }

//    public List<Map<String, Object>> getAllData(){
//        String tableName = "dbujisistem.salesline";
//        String sql = String.format("SELECT * FROM %s", tableName);
//        return jdbcTemplate.query(sql,
//                (resultSet, rowNum) -> {
//                    ResultSetMetaData metaData = resultSet.getMetaData();
//                    int columnCount = metaData.getColumnCount();
//
//                    Map<String, Object> dataMap = new HashMap<>();
//                    for (int i = 1; i<= columnCount; i++) {
//                        String columnName = metaData.getColumnName(i);
//                        Object columnValue = resultSet.getObject(i);
//                        dataMap.put(columnName, columnValue);
//                    }
//                    return dataMap;
//                });
//    }

//    public void insertData(Map<String, Object> dataMap) {
//        String tableName = "ujisistemc.saleslineframe";
//        String columns = String.join(", ", dataMap.keySet());
//        String placeholders = String.join(", ", Collections.nCopies(dataMap.size(), "?"));
//        String sql = String.format("INSERT INTO %s (%s) VALUES (%s)", tableName, columns, placeholders);
//        jdbcTemplate.update(sql, dataMap.values().toArray());
//    }
    
    public void insertData(List<Map<String, Object>> dataList, String tableName) {
        List<String> column = new ArrayList<>();
        for (Map<String, Object> data : dataList) {
            column.addAll(data.keySet());
            break;
        }
        String colName = String.join(",", column);
        String template = "INSERT INTO " + tableName + " (" + colName + ")";

        List<String> allValue = new ArrayList<>();
        for (Map<String, Object> data : dataList) {
            List<String> value = new ArrayList<>();
            for (Object obj : data.values()) {
                value.add("'" + obj.toString() + "'");
            }
            String joinValue = String.join(",", value);
            String wrapValue = "(" + joinValue + ")";
            allValue.add(wrapValue);
        }

        String joinAllValue = String.join(",", allValue);

        String sql = template + " VALUES " + joinAllValue;

        jdbcTemplate.batchUpdate(sql);

        System.out.println(sql);
    }

//    public void insertData(List<Map<String, Object>> dataList, String tableName) {
//        String columns = String.join(", ", dataList.get(0).keySet());
//
//        List<Object[]> batchParams = new ArrayList<>();
//
//        for (Map<String, Object> dataMap : dataList) {
//            Object[] values = dataMap.values().toArray();
//            batchParams.add(values);
//        }
//
//        String placeholders = String.join(", ", Collections.nCopies(dataList.get(0).size(), "?"));
//
//        String sql = String.format("INSERT INTO %s (%s) VALUES (%s)", tableName, columns, placeholders);
//
//        jdbcTemplate.batchUpdate(sql, batchParams);
//    }
    
    public List<String> getAllTableNames() {
        String query = "SELECT table_name FROM system_schema.tables WHERE keyspace_name = 'dbujisistem';";
        return jdbcTemplate.queryForList(query, String.class);
    }

    public List<String> getColumnList(String table) {
        String sql = "SELECT column_name FROM system_schema.columns WHERE keyspace_name = 'dbujisistem' AND table_name = ?";
        List<String> columnList = new ArrayList<>();

        jdbcTemplate.query(sql, new Object[]{table}, (rs, rowNum) -> {
            String columnName = rs.getString("column_name");
            columnList.add(columnName);
            return null;
        });

        return columnList;
    }
    
    public void createTable(List<String> columns, String tableName) {
        String keyspace = "dbujisistem";
//        String tableName = "dbujisistem.coba";
        List<String> columnDefinitions = new ArrayList<>();

        for (String col : columns) {
            String columnDefinition = col + " varchar";
            columnDefinitions.add(columnDefinition);
        }
        
        columnDefinitions.add("PRIMARY KEY (RCVNO)");

        String cols = String.join(", ", columnDefinitions);

        String cql = String.format("CREATE TABLE IF NOT EXISTS %s.%s (%s)", keyspace, tableName, cols);

        try (CqlSession session = CqlSession.builder().build()) {
            session.execute(cql);
            System.out.println("Success create table");
        } catch (Exception e) {
            System.out.println("Error creating table: " + e.getMessage());
        }
    }

//    public String createTable(List<String> columns) {
//        String tableName = "dbujisistem.coba";
//        List<String> column = new ArrayList<>();
//
//        for (String col : columns) {
//            String sqlCol = col + " varchar";
//            column.add(sqlCol);
//        }
//
//        column.add("PRIMARY KEY (RCVNO)");
//
//        String cols = String.join(", ", column);
//
//        String sql = String.format("CREATE TABLE %s (%S)", tableName, cols);
//        jdbcTemplate.update(sql);
//
//        return tableName;
//    }
    
//    public void createTableWithMap(List<String> columns, String tableName) {
//        String column = String.join(",", columns);
//        String sql = String.format("CREATE TABLE IF NOT EXISTS %s (%S)", tableName, column);
//        System.out.println("Success create table");
//        jdbcTemplate.update(sql);
//    }
    
    public void createTableWithMap(List<String> columns, String tableName) {
        String keyspace = "dbujisistem";
        List<String> columnDefinitions = new ArrayList<>();

        for (String col : columns) {
            String columnDefinition = col + " varchar";
            columnDefinitions.add(columnDefinition);
        }

        columnDefinitions.add("PRIMARY KEY (RCVNO)");
        String cols = String.join(", ", columnDefinitions);

        String cql = String.format("CREATE TABLE IF NOT EXISTS %s.%s (%s)", keyspace, tableName, cols);

        try (CqlSession session = CqlSession.builder().build()) {
            session.execute(cql);
            System.out.println("Success create table");
        } catch (Exception e) {
            System.out.println("Error creating table: " + e.getMessage());
        }
    }
    
    public void mencoba(List<Map<String, Object>> dataList) {
        String table = "tb_cb";

        List<String> column = new ArrayList<>();
        for (Map<String, Object> data : dataList) {
            column.addAll(data.keySet());
            break;
        }
        String colName = String.join(",", column);
        String template = "INSERT INTO " + table + " (" + colName + ")";

        List<String> allValue = new ArrayList<>();
        for (Map<String, Object> data : dataList) {
            List<String> value = new ArrayList<>();
            for (Object obj : data.values()) {
                value.add("'" + obj.toString() + "'");
            }
            String joinValue = String.join(",", value);
            String wrapValue = "(" + joinValue + ")";
            allValue.add(wrapValue);
        }

        String joinAllValue = String.join(",", allValue);

        String sql = template + " VALUES " + joinAllValue;

        //jdbcTemplate.executeCql(sql);
        System.out.println(sql);
    }

    // ... methods for other operations like getAllTableNames, getColumnList, createTable, etc.

}
