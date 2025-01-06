package org.example.simpleDb;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SimpleDb {
    private final static String DB_URL = "jdbc:mysql://";
    private final String hostName;
    private final String username;
    private final String password;
    private final String schema;
    private  Connection conn;
    private boolean autoCommit = true;
    private boolean devMode = false;
    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    public SimpleDb(String hostName, String username, String password, String schema) throws SQLException {
        this.hostName = hostName;
        this.username = username;
        this.password = password;
        this.schema = schema;
        conn = DriverManager.getConnection(DB_URL + hostName + "/" + schema, username, password);
    }

    public void setDevMode(boolean mode) {
        devMode = mode;
    }

    public void run(String statement, Object... args) {
        PreparedStatement pstmt=null;
        try  {
            getConnection();
            pstmt = conn.prepareStatement(statement);
            List<Object> argsList = Arrays.asList(args);
            if (argsList.isEmpty() || argsList.size() != args.length)
                throw new IllegalArgumentException("인수가 잘못 입력되었습니다.");

            for (int i = 0; i < argsList.size(); i++)
                pstmt.setObject(i + 1, args[i]);

            pstmt.execute();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private synchronized void getConnection() throws SQLException {
        conn = DriverManager.getConnection(DB_URL + hostName + "/" + schema, username, password);
        conn.setAutoCommit(autoCommit);
    }

    public void run(String statement) {
        try  {
            getConnection();
            Statement stmt = conn.createStatement();
            stmt.execute(statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Sql genSql() {
        try{
            getConnection();
            return new Sql(conn, this.objectMapper);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void startTransaction() {
        try {
            autoCommit = false;
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void rollback() {
        try {
            conn.rollback();
            autoCommit = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void commit() {
        try {
            conn.commit();
            autoCommit = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
