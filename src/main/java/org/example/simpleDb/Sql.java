package org.example.simpleDb;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Article;

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
        s.append(statement).append(" ");
        argsList.addAll(Arrays.asList(args));
        return this;
    }

    public long insert() {
        int id = 0;
        try (PreparedStatement pstmt = conn.prepareStatement(s.toString())) {
            addArgs(pstmt);

            id = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public int update() {
        int rowCnt = 0;
        try (PreparedStatement pstmt = conn.prepareStatement(s.toString())) {
            addArgs(pstmt);

            rowCnt = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowCnt;
    }

    public int delete() {
        int rowCnt = 0;
        try (PreparedStatement pstmt = conn.prepareStatement(s.toString())) {
            addArgs(pstmt);

            rowCnt = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowCnt;
    }

    public List<Map<String, Object>> selectRows() {
        try (PreparedStatement pstmt = conn.prepareStatement(s.toString())) {
            addArgs(pstmt);

            ResultSet rs = pstmt.executeQuery();
            List<Map<String, Object>> rows = new ArrayList<>();
            while (rs.next()) {
                rows.add(Map.of("id", rs.getLong("id"),
                        "title", rs.getString("title"),
                        "body", rs.getString("body"),
                        "createdDate", rs.getTimestamp("createdDate").toLocalDateTime(),
                        "modifiedDate", rs.getTimestamp("modifiedDate").toLocalDateTime(),
                        "isBlind", rs.getBoolean("isBlind")));
            }
            return rows;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public  List<Article> selectRows(Class<?> className) {
        List<Article> rows = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(s.toString())) {
            addArgs(pstmt);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                rows.add(new Article.Builder()
                                .id(rs.getLong("id"))
                                .title(rs.getString("title"))
                                .body(rs.getString("body"))
                                .createdDate(rs.getTimestamp("createdDate").toLocalDateTime())
                                .modifiedDate(rs.getTimestamp("modifiedDate").toLocalDateTime())
                                .isBlind(rs.getBoolean("isBlind"))
                                .build()
                );
            }
            return rows;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rows;
    }

    public Map<String, Object> selectRow() {
        try (PreparedStatement pstmt = conn.prepareStatement(s.toString())) {
            addArgs(pstmt);

            ResultSet rs = pstmt.executeQuery();
            Map<String, Object> row = new HashMap<>();
            while (rs.next()) {
                row.put("id", rs.getLong("id"));
                row.put("title", rs.getString("title"));
                row.put("body", rs.getString("body"));
                row.put("createdDate", rs.getTimestamp("createdDate").toLocalDateTime());
                row.put("modifiedDate", rs.getTimestamp("modifiedDate").toLocalDateTime());
                row.put("isBlind", rs.getBoolean("isBlind"));
            }
            return row;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public  Article selectRow(Class<?> className) {
        try (PreparedStatement pstmt = conn.prepareStatement(s.toString())) {
            addArgs(pstmt);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                return new Article.Builder()
                        .id(rs.getLong("id"))
                        .title(rs.getString("title"))
                        .body(rs.getString("body"))
                        .createdDate(rs.getTimestamp("createdDate").toLocalDateTime())
                        .modifiedDate(rs.getTimestamp("modifiedDate").toLocalDateTime())
                        .isBlind(rs.getBoolean("isBlind"))
                        .build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public LocalDateTime selectDatetime() {
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(s.toString());
            while (rs.next()) {
                return rs.getTimestamp(1).toLocalDateTime();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Long selectLong() {
        try (PreparedStatement stmt = conn.prepareStatement(s.toString())) {
            addArgs(stmt);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String selectString() {
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(s.toString());
            while (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Boolean selectBoolean() {
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(s.toString());
            while (rs.next()) {
                return rs.getBoolean(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Sql appendIn(String statement, Object... args) {
        s.append(statement).append(" ");
        int idx = s.lastIndexOf("?");
        s.replace(idx, idx + 1, String.join(", ",Arrays.stream(args).map(
                arg-> "\"" + arg + "\""
        ).toList()));
        return this;
    }

    public List<Long> selectLongs() {
        List<Long> res = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(s.toString())) {
            addArgs(stmt);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                res.add(rs.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    private void addArgs(PreparedStatement pstmt) throws SQLException {
        for (int i = 0; i < argsList.size(); i++) {
            pstmt.setObject(i + 1, argsList.get(i));
        }
    }
}
