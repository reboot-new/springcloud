package com.tan.springcloud2producer.helper;

import java.sql.Connection;
import java.sql.SQLException;

public class DbHelper {

    /**
     * 开始事务
     * @param cnn
     */
    public static void beginTransaction(Connection cnn){
        if(cnn!=null){
            try {
                if(cnn.getAutoCommit()){
                    cnn.setAutoCommit(false);
                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * 提交事务
     * @param cnn
     */
    public static void commitTransaction(Connection cnn){
        if(cnn!=null){
            try {
                if(!cnn.getAutoCommit()){
                    cnn.commit();
                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * 回滚事务
     * @param cnn
     */
    public static void rollBackTransaction(Connection cnn){
        if(cnn!=null){
            try {
                if(!cnn.getAutoCommit()){
                    cnn.rollback();
                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭连接
     * @param con
     */
    public static  void closeConnection(Connection con){
        // 关闭连接通道
        try {
            if (con != null && (!con.isClosed())) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static final String DRIVER_ORACLE  = "oracle.jdbc.OracleDriver";
    public static final String DRIVER_MSSQL = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    public static final String DRIVER_MYSQL = "";

}
