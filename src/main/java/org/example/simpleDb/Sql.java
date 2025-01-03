package org.example.simpleDb;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class Sql {
    private final Connection conn;
    private final ObjectMapper objectMapper;
    private StringBuilder s = new StringBuilder();
    private List<Object> argsList = new ArrayList<>();


    public Sql(Connection conn, ObjectMapper objectMapper) {
        this.conn = conn;
        this.objectMapper = objectMapper;
    }

    public Sql append(String statement) {
        s.append(statement).append(" ");
        return this;
    }

    public Sql append(String statement, Object... args) {
        s.append(statement);
        argsList.addAll(Arrays.asList(args));
        return this;
    }

    public long insert() {
        int id = 0;
        try(PreparedStatement pstmt = conn.prepareStatement(s.toString())) {
            for(int i = 0; i < argsList.size(); i++)
                pstmt.setObject(i+1, argsList.get(i));

            id = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public int update() {
        int rowCnt =0;
        try(PreparedStatement pstmt = conn.prepareStatement(s.toString())) {
            for(int i = 0; i < argsList.size(); i++)
                pstmt.setObject(i+1, argsList.get(i));

            rowCnt = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowCnt;
    }

    public int delete() {
        int rowCnt =0;
        try(PreparedStatement pstmt = conn.prepareStatement(s.toString())) {
            for(int i = 0; i < argsList.size(); i++)
                pstmt.setObject(i+1, argsList.get(i));

            rowCnt = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowCnt;
    }

    public List<Map<String, Object>> selectRows() {
        try(PreparedStatement pstmt = conn.prepareStatement(s.toString())) {
            for(int i = 0; i < argsList.size(); i++)
                pstmt.setObject(i+1, argsList.get(i));

            ResultSet rs = pstmt.executeQuery();
            List<Map<String, Object>> rows = new ArrayList<>();
            while(rs.next()){
                rows.add(Map.of("id", rs.getLong("id")));
                rows.add(Map.of("title", rs.getString("title")));
                rows.add(Map.of("body", rs.getString("body")));
                rows.add(Map.of("createdDate", rs.getTimestamp("createdDate").toLocalDateTime()));
                rows.add(Map.of("modifiedDate", rs.getTimestamp("modifiedDate").toLocalDateTime()));
                rows.add(Map.of("blind", rs.getBoolean("blind")));
            }
            return rows;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    public <T> List<T> selectRows(Class<T> className) {
        return null;
    }

    public Map<String, Object> selectRow() {
        return null;
    }
    public <T> T selectRow(Class<T> className) {
        return null;
    }

    public LocalDateTime selectDatetime() {
        try(Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT NOW();");
            while(rs.next()){
                return rs.getTimestamp(1).toLocalDateTime();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Long selectLong() {
        return null;
    }

    public String selectString() {
        return null;
    }

    public Boolean selectBoolean() {
        return null;
    }

    public Sql appendIn(String statement, Object... args) {
        return null;
    }

    public List<Long> selectLongs() {
        return null;
    }
}
