package org.example.simpleDb;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class Sql {
    public Sql append(String statement) {
    }
    public Sql append(String statement, Object... args) {
    }

    public long insert() {
        return 0;
    }

    public int update() {
    }

    public int delete() {
    }

    public List<Map<String, Object>> selectRows() {
    }
    public <T> List<T> selectRows(Class<T> className) {
    }

    public Map<String, Object> selectRow() {
    }
    public <T> T selectRow(Class<T> className) {
    }

    public LocalDateTime selectDatetime() {
    }

    public Long selectLong() {
    }

    public String selectString() {
    }

    public Boolean selectBoolean() {
    }

    public Sql appendIn(String statement, Object... args) {

    }

    public List<Long> selectLongs() {
    }
}
