package com.ujisistemcassandra.repository;

import com.ujisistemcassandra.converter.DateConverter;
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
        String cassandraTable = "ujisistemc." + tableName;
        String sql = String.format("SELECT * FROM %s", cassandraTable);
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            Map<String, Object> dataMap = new HashMap<>();
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnName(i);
                Object columnValue = resultSet.getObject(i);
                dataMap.put(columnName, columnValue);
            }

            return dataMap;
        });
    }

//    public void insertData(Map<String, Object> dataMap) {
//        String tableName = "saleslineframe";
//        String columns = String.join(", ", dataMap.keySet());
//        String placeholders = String.join(", ", Collections.nCopies(dataMap.size(), "?"));
//
//        String sql = String.format("INSERT INTO %s (%s) VALUES (%s)", tableName, columns, placeholders);
//        jdbcTemplate.update(sql, dataMap.values().toArray());
//    }

//    public void insertData(List<Map<String, Object>> dataList, String tableName) {
//        try {
//            if (dataList.isEmpty()) {
//                System.out.println("Data list is empty.");
//                return;
//            }
//
//            Map<String, Object> firstRow = dataList.get(0);
//            String columns = String.join(", ", firstRow.keySet());
//            String placeholders = String.join(", ", Collections.nCopies(firstRow.size(), "?"));
//
//            List<Object[]> batchParams = new ArrayList<>();
//            for (Map<String, Object> dataMap : dataList) {
//                Object[] values = dataMap.values().toArray();
//                batchParams.add(values);
//            }
//
//            String sql = String.format("INSERT INTO %s (%s) VALUES (%s)", tableName, columns, placeholders);
//            jdbcTemplate.batchUpdate(sql, batchParams);
//        } catch (DataAccessException e) {
//            System.out.println("Error executing SQL statement: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }

    public void insertData(List<Map<String,Object>> dataList, String tableName) {
        String cassandraTable = "ujisistemc." + tableName;
        List<String> column = new ArrayList<>();
        for (Map<String, Object> data : dataList) {
            column.addAll(data.keySet());
            break;
        }
        String colName = String.join(",", column);
        String template = "INSERT INTO" + " " + cassandraTable + " " + "(" + colName +")";

        List<String> allValue = new ArrayList<>();
        List<String> value = new ArrayList<>();
        for (Map<String, Object> data : dataList) {
            value.clear();
            for (Object obj : data.values()) {
                value.add("'" + obj.toString() + "'");
            }
            String joinValue = String.join(",", value);
            String wrapValue = "(" + joinValue +")";

            allValue.add(wrapValue);
        }

        String joinAllValue = String.join(",", allValue);

        String sql = template + " VALUES " + joinAllValue;

        jdbcTemplate.update(sql);

        System.out.println(sql);
    }

    public void insertDataWithMap(List<Map<String,Object>> dataList, Map<String, Object> dataType, String tableName) {
        String cassandraTable = "ujisistemc." + tableName;
        List<String> column = new ArrayList<>();
        DateConverter dateConverter = new DateConverter();

        for (Map<String, Object> data : dataList) {
            column.addAll(data.keySet());
            break;
        }
        String colName = String.join(",", column);
        String template = "INSERT INTO" + " " + cassandraTable + " " + "(" + colName +")";

        List<String> allValue = new ArrayList<>();
        List<String> value = new ArrayList<>();
        System.out.println(dataType);
        for (Map<String, Object> data : dataList) {
            value.clear();
            for (Object type : dataType.keySet()) {
                if (Objects.equals(dataType.get(type).toString(), "int")) {
                    value.add(data.get(type).toString());
                } else if (Objects.equals(dataType.get(type).toString(), "varchar")) {
                    value.add("'" + data.get(type).toString() + "'");
                } else if (Objects.equals(dataType.get(type).toString(), "date")) {
                    value.add("'" + dateConverter.cassandraDate(data.get(type).toString()) + "'");
                } else if (Objects.equals(dataType.get(type).toString(), "float")) {
                    value.add(data.get(type).toString());
                }
            }

            String joinValue = String.join(",", value);
            String wrapValue = "(" + joinValue +")";

            String sql = template + " VALUES " + wrapValue;
            jdbcTemplate.update(sql);

//            allValue.add(wrapValue);
        }
//
//        String joinAllValue = String.join(",", allValue);
//
//        String sql = template + " VALUES " + joinAllValue;
//        System.out.println(sql);



    }

    public List<String> getAllTableNames() {
        String query = "SELECT table_name FROM system_schema.tables WHERE keyspace_name = 'ujisistemc';";
        return jdbcTemplate.queryForList(query, String.class);
    }

    public List<String> getColumnList(String table) {
        String sql = "SELECT column_name FROM system_schema.columns WHERE keyspace_name = 'ujisistemc' AND table_name = ?;";
        List<String> columnList = new ArrayList<>();

        jdbcTemplate.query(sql, new Object[]{table}, (rs, rowNum) -> {
            String columnName = rs.getString("column_name");
            columnList.add(columnName);
            return null;
        });

        return columnList;
    }

    public void createTable(List<String> columns, String tableName) {
        String cassandraTable = "ujisistemc." + tableName;
        List<String> column = new ArrayList<>();

        for (String col : columns) {
            String sqlCol = col + " VARCHAR(100)";
            column.add(sqlCol);
        }

        String cols = String.join(", ", column);

        String sql = String.format("CREATE TABLE IF NOT EXISTS %s (%S)", cassandraTable, cols);
        jdbcTemplate.update(sql);
    }

    public void createTableWithMap(List<String> columns, String tableName) {
        String cassandraTable = "ujisistemc." + tableName;
        String column = String.join(",", columns);
        String sql = String.format("CREATE TABLE IF NOT EXISTS %s (%S)", cassandraTable, column);
        System.out.println("Success create table");
        jdbcTemplate.update(sql);
    }

    public void mencoba(List<Map<String, Object>> dataList) {
        String insert = "INSERT INTO";
        String table = "tb_cb";




        List<String> column = new ArrayList<>();
        for (Map<String, Object> data : dataList) {
            column.addAll(data.keySet());
            break;
        }
        String colName = String.join(",", column);
        String template = insert + " " + table + " " + "(" + colName +")";

        List<String> allValue = new ArrayList<>();
        List<String> value = new ArrayList<>();
        for (Map<String, Object> data : dataList) {
            value.clear();
            for (Object obj : data.values()) {
                value.add("'" + obj.toString() + "'");
            }
            String joinValue = String.join(",", value);
            String wrapValue = "(" + joinValue +")";

            allValue.add(wrapValue);
        }

        String joinAllValue = String.join(",", allValue);

        String sql = template + " VALUES " + joinAllValue;

        System.out.println(sql);



//        System.out.println(sql);
    }
}
