package com.tan.springcloud2producer.controller;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;

/**
 * Druid数据库连接池
 *
 */
@RestController
@RequestMapping("/druid")
public class DruidController {

    /**
     * 达梦
     */
    @RequestMapping("/dm")
    public void dm() throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl("jdbc:dm://192.168.2.160:5236");
        dataSource.setDriverClassName("dm.jdbc.driver.DmDriver");
        dataSource.setUsername("SYSDBA");
        dataSource.setPassword("elitel");
        try {
            // 获得连接:
            conn = dataSource.getConnection();
            // 编写SQL：
            String sql = "select * from PERSON.ADDRESS";
            pstmt = conn.prepareStatement(sql);
            // 执行sql:
            rs = pstmt.executeQuery();

            while (rs.next()) {
                System.out.println(rs.getString("ADDRESSID") + "   " + rs.getString("ADDRESS1"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rs.close();
            pstmt.close();
            conn.close();
        }
    }

    /**
     * 人大金仓
     */
    @RequestMapping("/kingbase")
    public void kingbase() throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl("jdbc:kingbase8://192.168.2.160:54321/RWDB");
        dataSource.setDriverClassName("com.kingbase8.Driver");
        dataSource.setUsername("SYSTEM");
        dataSource.setPassword("elitel");
        try {
            // 获得连接:
            conn = dataSource.getConnection();
            // 编写SQL：
            String sql = "select * from \"TEMP_TABLe\"";
            pstmt = conn.prepareStatement(sql);
            // 执行sql:
            rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString("ID") + "   " + rs.getString("NAME") );
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rs.close();
            pstmt.close();
            conn.close();
        }
    }

    /**
     * 南大通用
     */
    @RequestMapping("/gbase")
    public void gbase() throws Exception {

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl("jdbc:gbase://192.168.2.160:5258/gbase");
        dataSource.setDriverClassName("com.gbase.jdbc.Driver");
        dataSource.setUsername("root");
        dataSource.setPassword("elitel");
        try {
            // 获得连接:
            conn = dataSource.getConnection();
            // 编写SQL：
            String sql = "select * from user";
            pstmt = conn.prepareStatement(sql);
            // 执行sql:
            rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString("HOST") + "   " + rs.getString("USER") );
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rs.close();
            pstmt.close();
            conn.close();
        }
    }

    /**
     * 神州通用
     */
    @RequestMapping("/shentong")
    public void shentong() throws Exception {

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl("jdbc:oscar://192.168.2.160:2003/OSRDB");
        dataSource.setDriverClassName("com.oscar.Driver");
        dataSource.setUsername("SYSDBA");
        dataSource.setPassword("elitel");
        try {
            // 获得连接:
            conn = dataSource.getConnection();
            // 编写SQL：
            String sql = "select * from TEST";
            pstmt = conn.prepareStatement(sql);
            // 执行sql:
            rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString("ID") + "   " + rs.getString("NAME") );
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rs.close();
            pstmt.close();
            conn.close();
        }
    }

    public static void main(String[] args) throws Exception {
        DruidController c = new DruidController();
        c.kingbase();
    }
}
