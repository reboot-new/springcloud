package com.tan.springcloud2.controller;

import com.microsoft.sqlserver.jdbc.SQLServerDriver;
import io.swagger.models.auth.In;
import jdk.nashorn.internal.objects.annotations.Constructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;
import java.util.*;
import java.util.Date;


/**
 * 通过mssql的链接服务器查询
 */
@RestController
@RequestMapping("/link")
public class LinkController {

    private String url;
    private String user;
    private String password;
    private Connection con;
    private Statement statement;

    public LinkController() throws Exception {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            url = "jdbc:sqlserver://127.0.0.1:1433;database=master";
            user = "sa";
            password = "Effective";
            con = DriverManager.getConnection(url, user, password);
            statement = con.createStatement();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/all")
    public void ExecAll() throws Exception {
        OracleAndMssql("2015-8-1");
        Mssql();
        Oracle();
    }

    /**
     * 混合oracle和mssql查询
     */
    @RequestMapping("/oam")
    public String OracleAndMssql(String beginDate) throws Exception {
        if (beginDate == null || beginDate =="") {beginDate = "2015-8-1";}
        String sql  = String.format("SELECT mssql.*,orcl.TM,orcl.DRP FROM OPENQUERY([ORCL],'SELECT * from ST_PPTN_R where TM > TO_DATE(''%s'', ''yyyy-mm-dd'')') AS orcl INNER JOIN OPENQUERY([135],'SELECT * FROM base_rwdb.dbo.ST_STBPRP_B') AS mssql ON orcl.STCD=mssql.STCD",beginDate);
        List<Map<String, Object>> res = QueryBySQL(sql);
        return "ok";
    }

    /**
     * mssql查询
     */
    @RequestMapping("/mssql")
    public String Mssql() throws Exception {
        ExecMssql();
        return "ok";
    }

    private List<Map<String, Object>> ExecMssql() throws Exception{
        String sql = "SELECT * FROM OPENQUERY([135],'SELECT * FROM base_rwdb.dbo.ST_STBPRP_B')";
        List<Map<String, Object>> rs = QueryBySQL(sql);
        return rs;
    }

    /**
     * Oracle查询
     */
    @RequestMapping("/oracle")
    public String Oracle() throws Exception {
        List<Map<String, Object>> rs = ExecOracle();
        return "ok";
    }

    private List<Map<String, Object>> ExecOracle() throws Exception{
        String sql = "SELECT * FROM OPENQUERY([ORCL],'SELECT * from ST_PPTN_R where TM > TO_DATE(''2015-8-1'', ''yyyy-mm-dd'')')";
        List<Map<String, Object>> rs = QueryBySQL(sql);
        return rs;
    }

    @RequestMapping("/memory")
    public String MemoryJoin() throws Exception{
        List<Map<String, Object>> oracleRes = ExecOracle();
        List<Map<String, Object>> mssqlRes = ExecMssql();
        List<Map<String, Object>> res = Descartes(oracleRes,mssqlRes,"STCD");
        return "OK";
    }

    /**
     * 执行sql语句
     * @param sql
     * @return
     * @throws Exception
     */
    private ResultSet ExecSQL(String sql) throws Exception {
        long startTime = System.currentTimeMillis();
        ResultSet rs = statement.executeQuery(sql);
        long endTime = System.currentTimeMillis();
        System.out.println(String.format("【sql执行耗时】=========%f秒",(endTime-startTime)/1000.0));
        return rs;
    }

    /**
     * ResultSet转换成List
     *
     * @param rs
     * @return
     * @throws Exception
     */
    private List<Map<String, Object>> ORM(ResultSet rs) throws Exception {
        long startTime = System.currentTimeMillis();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        //获得结果集结构信息,元数据
        ResultSetMetaData md = rs.getMetaData();
        //获得列数
        int columnCount = md.getColumnCount();
        while (rs.next()) {
            Map<String, Object> rowData = new HashMap<String, Object>();
            for (int i = 1; i <= columnCount; i++) {
                rowData.put(md.getColumnName(i), rs.getObject(i));
            }
            list.add(rowData);
        }
        long endTime = System.currentTimeMillis();
        System.out.println(String.format("【ORM执行耗时】=========%f秒",(endTime-startTime)/1000.0));
        return list;
    }


    /**
     * 执行sql，返回list
     * @param sql
     * @return
     * @throws Exception
     */
    private List<Map<String, Object>> QueryBySQL(String sql) throws Exception{
        return ORM(ExecSQL(sql));
    }


    /**
     * 笛卡尔积计算
     *
     * @param leftP
     * @param rightP
     * @param key
     * @return
     */
    private List<Map<String, Object>> Descartes(
                List<Map<String, Object>> leftP,
                List<Map<String, Object>> rightP,
                String key){
        List<Map<String, Object>> res = new ArrayList<Map<String, Object>>();
        String leftKey = "";
        long startTime = System.currentTimeMillis();
        Map<String,Map<String, Object>> rightMap = List2MapByKey(rightP,key);
        /*遍历列表，用对应的key去连接匹配*/
        for (Map<String, Object> lefte:leftP) {
             leftKey =  lefte.get(key).toString();
             if (rightMap.containsKey(leftKey)){
                 lefte.putAll(rightMap.get(leftKey));
                 res.add(lefte);
             }
        }
        long endTime = System.currentTimeMillis();
        System.out.println(String.format("【笛卡尔积执行耗时】=========%f秒",(endTime-startTime)/1000.0));
        return res;
    }

    /**
     * 通过指定key，规制list
     * @return
     */
    private Map<String,Map<String, Object>> List2MapByKey(
                List<Map<String, Object>> dataList,
                String key){

        Map<String,Map<String, Object>> res = new HashMap<String,Map<String, Object>>();
        for (Map<String, Object> e: dataList) {
            res.put(e.get(key).toString(),e);
        }
        return res;
    }

    @RequestMapping("/stbprp")
    public List<ExtStStbprpB> stbprp() {
        List<ExtStStbprpB> list = new ArrayList<ExtStStbprpB>();
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://192.168.0.135:1433;database=rwdb";
            String user = "sa";
            String password = "elitel!@3$";
            Connection con = DriverManager.getConnection(url, user, password);
            Statement statement = con.createStatement();
//            ResultSet re = statement.executeQuery("SELECT DISTINCT name FROM sys.tables");//执行查询语句，接收结果集
//            ResultSet re = statement.executeQuery("SELECT DISTINCT addvnm as name FROM OPENQUERY([241],'SELECT * from ST_ADDVCD_D') ");
            ResultSet re = statement.executeQuery(
                    "SELECT b.STCD,b.STNM,b.STLC,a.ADDVNM FROM OPENQUERY([mysql], 'SELECT\t* from st_addvcd_d;') AS a INNER JOIN dbo.ST_STBPRP_B AS b ON b.ADDVCD = a.ADDVCD;");

            int ColumCount = re.getMetaData().getColumnCount();

            while (re.next()) {
                ExtStStbprpB st = new ExtStStbprpB(re.getString("STCD"),
                        re.getString("STNM"),
                        re.getString("STLC"),
                        re.getString("ADDVNM"));
                list.add(st);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    class ExtStStbprpB {
        public String STCD;
        public String STNM;
        public String STLC;
        public String ADDVNM;

        public ExtStStbprpB() {
        }

        public ExtStStbprpB(String stcd, String stnm, String stlc, String addvnm) {
            this.STCD = stcd;
            this.STNM = stnm;
            this.STLC = stlc;
            this.ADDVNM = addvnm;
        }
    }
}


