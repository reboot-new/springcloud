package com.tan.springcloud2.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/oracle")
public class OracleController {

    @RequestMapping("/pptn")
    public Object pptn() throws Exception {
        String driver = "oracle.jdbc.OracleDriver";

        String url = "jdbc:oracle:thin:@192.168.0.34:1521/orcl";
        String user = "base_rwdb";
        String password = "elitel!@3$";
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        boolean flag = false;
//        String dd = datep;
        try {
            Class.forName(driver) ;
            con = DriverManager.getConnection(url, user, password);
            String sql = "SELECT * from ST_PPTN_R where TM > TO_DATE('2015-8-1', 'yyyy-mm-dd')";
            System.out.println(new java.util.Date());
            long startTime = System.currentTimeMillis();
            Statement st = con.createStatement();
            rs= st.executeQuery(sql);
//            pstm = con.prepareStatement(sql);
//            rs = pstm.executeQuery();

            List<String> list = new ArrayList<String>();

            while (rs.next()){
                list.add(rs.getString(1));
                list.add(rs.getString(2));
                list.add(rs.getString(3));
                list.add(rs.getString(4));
            }
            long endTime = System.currentTimeMillis();
            System.out.println(String.format("odbc查询oracle耗时：%d",endTime-startTime));
//            System.out.println(list.size());
//            rs.next();

//            double sum = 0;
//            for (int i = 0;i < 10; ++i){
//                long startTime = System.currentTimeMillis();
//                rs = pstm.executeQuery();
//                rs.next();
//                System.out.println(rs.getString(1));
//                long endTime = System.currentTimeMillis();
//                double tc = (endTime - startTime)/1000.0;
//                System.out.printf("第%d次查询oracle耗时：%f\n", i, tc);
//            }
//            System.out.printf("oracle查询平均耗时：%f\n", sum/10.0);
            flag = true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            // 关闭执行通道
            if (pstm != null) {
                try {
                    pstm.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

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

        if (flag) {
            System.out.println("执行成功！");
        } else {
            System.out.println("执行失败！");
        }
        return "ok";
    }
}
