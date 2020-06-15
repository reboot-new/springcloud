package com.tan.springcloud2producer.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.thread.ThreadUtil;
import com.tan.springcloud2producer.helper.DbHelper;
import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/oracle")
public class OracleController {


    @RequestMapping("/pptn")
    public Object pptn() throws Exception {

        Thread t1 = new Thread(new INSERTINTO("REAL"));
        Thread t2 = new Thread(new INSERTINTO("HIS"));
        t1.start();
        t2.start();

        t1.join();
        t2.join();
        return "ok";
    }


    @RequestMapping("/tongbu")
    public String tongbu() {
        new INSERTINTO("REAL").run();
        return "hello";
    }

    public Object deleteInsert() throws Exception {
        String driver = "oracle.jdbc.OracleDriver";

        String url = "jdbc:oracle:thin:@10.1.1.39:1521/FXDB";
        String user = "rwdbtest";
        String password = "YbZx_63202471";
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        boolean flag = false;
        try {
            Class.forName(driver);

            FileReader deleteFile = new FileReader("E:\\Elitel\\03_公司项目\\17_水情信息交换系统（水利部）\\04_BUG日志\\数据积压\\数据测试\\500_DELETE.txt");
            FileReader insertFile = new FileReader("E:\\Elitel\\03_公司项目\\17_水情信息交换系统（水利部）\\04_BUG日志\\数据积压\\数据测试\\500_INSERT.txt");

            List<String> updateSQLS = deleteFile.readLines();
            List<String> insertSQLS = insertFile.readLines();


            long startTime = System.currentTimeMillis();
            con = DriverManager.getConnection(url, user, password);


            DbHelper.beginTransaction(con);
            int insertC = 0;
            st = con.createStatement();

            for (int i = 0; i < 499; ++i) {
                st.addBatch(insertSQLS.get(i));
            }

            st.executeBatch();
            DbHelper.commitTransaction(con);
            long endTime = System.currentTimeMillis();
            System.out.println(String.format("SQL执行耗时：%f", (endTime - startTime) / 1000.0));

//            String delete = "DELETE ST_PPTN_R WHERE TM>= TO_DATE('2025-06-04 08:00','yyyy-mm-dd hh:mi') and TM <= TO_DATE('2025-06-04 09:00','yyyy-mm-dd hh:mi')";
            String delete = "DELETE ST_PPTN_R WHERE TM>= '2025-06-04 08:00' and TM <= '2025-06-04 09:00'";
            int deleteC = st.executeUpdate(delete);
            System.out.println("删除数据：" + deleteC);
            flag = true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            DbHelper.rollBackTransaction(con);
            e.printStackTrace();
        } finally {
            st.close();
            DbHelper.closeConnection(con);
        }

        if (flag) {
            System.out.println("执行成功！");
        } else {
            System.out.println("执行失败！");
        }
        return "ok";
    }


    public class INSERTINTO implements Runnable {

        String suffix = "";

        String driver, url, user, password;

        public INSERTINTO(String suffix) {
            this.suffix = suffix;
        }

        public INSERTINTO(String driver, String url, String user, String password, String suffix) {
            this.driver = driver;
            this.url = url;
            this.user = user;
            this.password = password;
            this.suffix = suffix;
        }

        @Override
        public void run() {

//            String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
//
//            String url = "jdbc:sqlserver://127.0.0.1:1433;DatabaseName=rwdb";
//            String user = "sa";
//            String password = "Effective";
            String driver = "oracle.jdbc.OracleDriver";
            String url = "jdbc:oracle:thin:@10.1.1.39:1521/fxdb";
            String user = "rwdbtest";
            String password = "YbZx_63202471";

//            String url = "jdbc:oracle:thin:@192.168.0.34:1521/ORCL";
//            String user = "test";
//            String password = "test";

            Connection con = null;
            Statement st = null;
            ResultSet rs = null;
            boolean flag = false;

            for (int j = 0; j < 1; ++j) {

                try {
                    Class.forName(driver);
//
//                    FileReader updateFile = new FileReader(String.format("E:\\Elitel\\03_公司项目\\17_水情信息交换系统（水利部）\\04_BUG日志\\数据积压\\数据测试\\5000_UPDATE_%s_MSSQL.txt", suffix));
//                    FileReader insertFile = new FileReader(String.format("E:\\Elitel\\03_公司项目\\17_水情信息交换系统（水利部）\\04_BUG日志\\数据积压\\数据测试\\5000_INSERT_%s_MSSQL.txt", suffix));

                    FileReader updateFile = new FileReader(String.format("E:\\Elitel\\03_公司项目\\17_水情信息交换系统（水利部）\\04_BUG日志\\数据积压\\数据测试\\500_UPDATE.txt"));
                    FileReader insertFile = new FileReader(String.format("E:\\Elitel\\03_公司项目\\17_水情信息交换系统（水利部）\\04_BUG日志\\数据积压\\数据测试\\500_INSERT.txt"));

                    List<String> updateSQLS = updateFile.readLines();
                    List<String> insertSQLS = insertFile.readLines();
                    long startTime = System.currentTimeMillis();

                    TimeInterval timer = DateUtil.timer();
                    con = DriverManager.getConnection(url, user, password);
//                    con.setCatalog("rwdb");
//                PrintWriter writer = new PrintWriter(System.out);
//                DriverManager.setLogWriter(writer);

                    System.out.println(String.format("第%d次获取连接耗时%f秒", j, timer.intervalRestart() / 1000.0));
                    DbHelper.beginTransaction(con);
                    System.out.println(String.format("第%d次开启事务耗时%f秒", j, timer.intervalRestart() / 1000.0));
                    int insertC = 0;
                    st = con.createStatement();
                    System.out.println(String.format("第%d次创建模板耗时%f秒", j, timer.intervalRestart() / 1000.0));

                    for (int i = 0; i < updateSQLS.size(); ++i) {
                        int c = st.executeUpdate(updateSQLS.get(i));
                        if (c == 0) {
                            st.executeUpdate(insertSQLS.get(i));
                            insertC++;
                        } else {
//                            System.out.println(updateSQLS.get(i));
                        }
                    }
                    if (suffix.equals("REAL")) {
//                        ThreadUtil.sleep(1000*2);
                    }
                    DbHelper.commitTransaction(con);
//                    st.close();
//                    DbHelper.closeConnection(con);
                    long endTime = System.currentTimeMillis();
                    System.out.println("共计插入数据：" + insertC);
                    System.out.println(String.format("第" + j + "次SQL执行耗时：%f", (endTime - startTime) / 1000.0));
//                    st.close();
//                    st = con.createStatement();
                    String delete = "DELETE ST_PPTN_R WHERE TM>= TO_DATE('2025-06-01 08:00','yyyy-mm-dd hh:mi') and TM <= TO_DATE('2025-07-04 09:00','yyyy-mm-dd hh:mi')";
//                    String delete = "DELETE ST_PPTN_R WHERE TM>= '2023-06-04 08:00' and TM <= '2027-06-04 09:00'";

//                    DbHelper.beginTransaction(con);
//                    int deleteC = st.executeUpdate(delete);
//                    DbHelper.commitTransaction(con);
//                    System.out.println("删除数据：" + deleteC);
//                    ThreadUtil.sleep(1000* 2);

//                    Connection con1 = DriverManager.getConnection(url, user, password);
//                    Statement st1 = con1.createStatement();
//                    st1.executeUpdate("DELETE ST_PPTN_R WHERE TM>= '2023-06-04 08:00' and TM <= '2027-06-04 09:00'");
//                    st1.close();
//                    con1.close();
                    flag = true;
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    DbHelper.rollBackTransaction(con);
                    e.printStackTrace();
                } finally {
                    try {
                        st.close();
                    } catch (Exception e) {
                    }

                    DbHelper.closeConnection(con);
                }
                if (flag) {
                    System.out.println("=============================执行成功==================================" + suffix);

                } else {
                    System.out.println("执行失败！");
                }
            }
        }
    }

    public void oracle() {

    }


    public void delete() throws Exception {
        String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

        String url = "jdbc:sqlserver://127.0.0.1:1433;DatabaseName=rwdb";
        String user = "sa";
        String password = "Effective";

        Class.forName(driver);

        Connection con = DriverManager.getConnection(url, user, password);
        Statement st = con.createStatement();
        st.executeUpdate("DELETE ST_PPTN_R WHERE TM>= '2023-06-04 08:00' and TM <= '2027-06-04 09:00'");
        st.close();
        con.close();
    }

    public static void main(String[] args) throws Exception {
        OracleController o = new OracleController();
        o.tongbu();
    }
}
