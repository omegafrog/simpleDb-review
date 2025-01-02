package org.example.simpleDb;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class Sql {
    public Sql append(String statement) {
        return null;
    }

    public Sql append(String statement, Object... args) {
        return null;

    }

    public long insert() {
        return 0;
    }

    public int update() {
        return 0;

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
