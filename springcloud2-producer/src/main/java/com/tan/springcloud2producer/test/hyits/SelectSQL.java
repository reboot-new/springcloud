package com.tan.springcloud2producer.test.hyits;

import cn.hutool.core.io.file.FileReader;
import com.tan.springcloud2producer.controller.OracleController;
import com.tan.springcloud2producer.helper.DbHelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class SelectSQL {
    public Object pptn() throws Exception {
        String driver = "oracle.jdbc.OracleDriver";

        String url = "jdbc:oracle:thin:@10.1.1.39:1521/FXDB";
        String user = "rwdbtest";
        String password = "YbZx_63202471";
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        boolean flag = false;
        try {
            Class.forName(driver) ;

            long startTime = System.currentTimeMillis();
            con = DriverManager.getConnection(url, user, password);
            DbHelper.beginTransaction(con);
            int insertC = 0;
            st = con.createStatement();

            String sql = "SELECT count(1) from ST_PPTN_R WHERE TM>= TO_DATE('2025-06-04 08:00','yyyy-mm-dd hh:mi') and TM <= TO_DATE('2025-06-04 09:00','yyyy-mm-dd hh:mi')";
            st.executeQuery(sql);
//            for (int i = 0; i < updateSQLS.size();++i) {
//
//                int c = st.executeUpdate(updateSQLS.get(i));
//                if (c==0){
//                    st.executeUpdate(insertSQLS.get(i));
//                    insertC++;
//                } else {
//                    System.out.println(updateSQLS.get(i));
//                }
//            }
            DbHelper.commitTransaction(con);
            long endTime = System.currentTimeMillis();
            System.out.println("共计插入数据："+insertC);
            System.out.println(String.format("SQL执行耗时：%f",(endTime-startTime)/1000.0));
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

    public static void main(String[] args) throws Exception {
        SelectSQL o = new SelectSQL();
        o.pptn();
    }
}
