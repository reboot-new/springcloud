package com.tan.springcloud2producer.helper;

import com.alibaba.fastjson.JSON;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;
import com.sun.jna.StringArray;
import com.tan.springcloud2producer.entity.JsonResult;

import java.util.ArrayList;
import java.util.List;

public class JnaHelper {
    /**
     * DLL动态库调用方法
     * @Description: 读取调用CDecl方式导出的DLL动态库方法
     * @author: LinWenLi
     * @date: 2018年7月18日 上午10:49:02
     */
    public interface CLibrary extends Library {
        // DLL文件默认路径为项目根目录，若DLL文件存放在项目外，请使用绝对路径。（此处：(Platform.isWindows()?"msvcrt":"c")指本地动态库msvcrt.dll）
//        CLibrary INSTANCE = (CLibrary) Native.loadLibrary("F:\\Code\\CPlusPlusCode\\MyDLLSolution\\x64\\Release\\MyDLLSolution",
//                CLibrary.class);


        // 声明将要调用的DLL中的方法,可以是多个方法(此处示例调用本地动态库msvcrt.dll中的printf()方法)
//        void printf(String format, Object... args);
        String AfterPointCut(String[] obj,int len);

//        double Add(double a,double b);
//        String Preprocess(String inputStr);
//        String GetYaoshi();
//        String Process(String obj);
//        String ProStr(byte[] obj);
//        void test(StringArray arg);
//        double ArrayCount(String[] arg);
    }

    public static void main(String[] args) {
        JsonResult pram = new JsonResult();
        pram.setMsg("我和我的祖国");
        pram.setSuccess(true);
//        System.out.println(JSON.toJSONString(pram));

//      F:\Code\CSharpCode\JNAdll\x64\Release\GB2312
        System.setProperty("jna.encoding","GBK");
        JnaHelper.CLibrary INSTANCE = Native.loadLibrary("F:\\Code\\CSharpCode\\JNAdll\\x64\\Release\\ClassLibrary1",
                JnaHelper.CLibrary.class);

//        JnaHelper.CLibrary INSTANCE = (JnaHelper.CLibrary) Native.loadLibrary("F:\\Code\\CSharpCode\\JNAdll\\x64\\Release\\Win32Project1",
//                JnaHelper.CLibrary.class);


        String param = "{'result':0,'message':'查询成功','data':{'total':16,'rows':[{'eq_type_name':'泵房','eq_model':'HC.WQX20-1','eq_code':'000001','eq_unit_name':'革寨1泵站管理所','eq_name':'泵房','id':'1','RN':1,'eq_producter_name':'凤岭公司','eq_parent_name':'革寨1泵站','tm':'2019-06-19 09:46:45'},{'eq_type_name':'泵房','eq_model':'HC.WQX20-1','eq_code':'000001','eq_unit_name':'革寨1泵站管理所','eq_name':'泵房','id':'1','RN':1,'eq_producter_name':'凤岭公司','eq_parent_name':'革寨1泵站','tm':'2019-06-19 09:46:45'},{'eq_type_name':'泵房','eq_model':'HC.WQX20-1','eq_code':'000001','eq_unit_name':'革寨1泵站管理所','eq_name':'泵房','id':'1','RN':1,'eq_producter_name':'凤岭公司','eq_parent_name':'革寨1泵站','tm':'2019-06-19 09:46:45'},{'eq_type_name':'泵房','eq_model':'HC.WQX20-1','eq_code':'000001','eq_unit_name':'革寨1泵站管理所','eq_name':'泵房','id':'1','RN':1,'eq_producter_name':'凤岭公司','eq_parent_name':'革寨1泵站','tm':'2019-06-19 09:46:45'}]}}";
        String param1 = "{'result':0,'message':'查询成功12313','data':{'total':16,'rows':[{'eq_type_name':'泵房','eq_model':'HC.WQX20-1','eq_code':'000001','eq_unit_name':'革寨1泵站管理所','eq_name':'泵房','id':'1','RN':1,'eq_producter_name':'凤岭公司','eq_parent_name':'革寨1泵站','tm':'2019-06-19 09:46:45'},{'eq_type_name':'泵房','eq_model':'HC.WQX20-1','eq_code':'000001','eq_unit_name':'革寨1泵站管理所','eq_name':'泵房','id':'1','RN':1,'eq_producter_name':'凤岭公司','eq_parent_name':'革寨1泵站','tm':'2019-06-19 09:46:45'},{'eq_type_name':'泵房','eq_model':'HC.WQX20-1','eq_code':'000001','eq_unit_name':'革寨1泵站管理所','eq_name':'泵房','id':'1','RN':1,'eq_producter_name':'凤岭公司','eq_parent_name':'革寨1泵站','tm':'2019-06-19 09:46:45'},{'eq_type_name':'泵房','eq_model':'HC.WQX20-1','eq_code':'000001','eq_unit_name':'革寨1泵站管理所','eq_name':'泵房','id':'1','RN':1,'eq_producter_name':'凤岭公司','eq_parent_name':'革寨1泵站','tm':'2019-06-19 09:46:45'}]}}";
//        String res =  INSTANCE.Process(param);
//        String res =  INSTANCE.ProStr(param.getBytes());
//        System.out.println(res.toString());

        List<String> lstStr = new ArrayList<>();
        lstStr.add(param);
        lstStr.add(param1);

        System.out.println("=================================");
        String[] arr = lstStr.toArray(new String[2]);
        StringArray sa = new StringArray(arr);
//        INSTANCE.test(sa);
        String[] ac = {"a","b"};
        String res =  INSTANCE.AfterPointCut(arr,2);
        System.out.println(res);
//        INSTANCE.ArrayCount(ac);
//        String[] ara =new String[2];
//        ara[0] = "4";
//        ara[1] = "5";
//        INSTANCE.test(ara);

//        CLibrary.INSTANCE.printf("Hello, World!");
//        double d = CLibrary.INSTANCE.Add(1,7777);
//        System.out.println(d);

//        String str = CLibrary.INSTANCE.Preprocess("你好，我是dll？？");
//        System.out.println(str);
//
//        String yaoshi = CLibrary.INSTANCE.GetYaoshi();
//        System.out.println(yaoshi);
    }
}
