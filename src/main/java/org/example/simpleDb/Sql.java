package org.example.simpleDb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Sql {
    private final Connection conn;
    private StringBuilder s = new StringBuilder();
    private List<Object> argsList = new ArrayList<>();

    public Sql(Connection conn) {
        this.conn = conn;
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
        int id =-1;
        try(PreparedStatement pstmt = conn.prepareStatement(s.toString())) {
            for(int i = 0; i < argsList.size(); i++)
                pstmt.setObject(i+1, argsList.get(i));

            id = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public int delete() {
        return 0;
    }

    public List<Map<String, Object>> selectRows() {
        return null;
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
