package com.suwy.coder.mapper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * @author laisy
 * @date 2019/2/20
 * @description
 */
public class ConnectionUtil {


        public static Connection getConn(String dbName, String username, String password) {
            String driver = "com.mysql.jdbc.Driver";
            String url = dbName+"?useUnicode=true&characterEncoding=utf-8&useSSL=false";
            Connection conn = null;
            try {
                Class.forName(driver);
                conn = (Connection) DriverManager.getConnection(url, username, password);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return conn;
        }

}