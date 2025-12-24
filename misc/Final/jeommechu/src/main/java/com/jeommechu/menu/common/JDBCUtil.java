package com.jeommechu.menu.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCUtil {

    public static Connection getConnection() {
        Connection conn = null;
        try {
            // JDBC 1단계 : 드라이버 객체 로딩
            // MySQL 드라이버 로딩
            Class.forName("com.mysql.cj.jdbc.Driver");

            // JDBC 2단계 : 커넥션 연결
            String jdbcUrl = "jdbc:mysql://localhost:3306/jeommechu?serverTimezone=UTC";
            String username = "root"; // MySQL 사용자 이름
            String password = "1234"; // MySQL 비밀번호
            conn = DriverManager.getConnection(jdbcUrl, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void close(PreparedStatement stmt, Connection conn) {
        // JDBC 5단계 : 연결 해제
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close(ResultSet rs, PreparedStatement stmt, Connection conn) {
        // JDBC 5단계 : 연결 해제
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        close(stmt, conn);
    }
}
