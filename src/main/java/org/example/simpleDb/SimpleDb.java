package org.example.simpleDb;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SimpleDb {
    private final static String DB_URL = "jdbc:mysql://";
    private final static List<Connection> connList = new ArrayList<>();
    private final static Integer MAX_CONNECTION = 10;
    private final Connection conn;
    private boolean devMode = false;
    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    public SimpleDb(String localhost, String username, String password, String schema) throws SQLException {
        conn = DriverManager.getConnection(DB_URL+localhost+"/"+schema, username, password);
    }

    public void setDevMode(boolean mode) {
        devMode = mode;
    }

    public void run(String statement, Object... args) {
        try(PreparedStatement pstmt = conn.prepareStatement(statement)) {
            List<Object> argsList = Arrays.asList(args);
            if(argsList.isEmpty() || argsList.size()!=args.length)
                throw new IllegalArgumentException("인수가 잘못 입력되었습니다.");

            for(int i = 0; i < argsList.size(); i++)
                pstmt.setObject(i+1, args[i]);

            pstmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void run(String statement){
        try(Statement stmt = conn.createStatement()) {
            stmt.execute(statement);
        }catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public Sql genSql() {
        return new Sql(this.conn, this.objectMapper);
    }

    public void close() {
        
    }

    public void startTransaction() {
        
    }

    public void rollback() {
    }

    public void commit() {
    }
}
