package com.tan.springcloud2.controller;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComException;
import com.jacob.com.ComFailException;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ac")
public class ActiveComController {


    @RequestMapping("/create")
    public String creata(){

        ActiveXComponent surfer = new ActiveXComponent("surfer.Application");
        return"";
    }

    /** 声明一个word对象*/
    private ActiveXComponent objWord;

    /** 声明四个word组件*/
    private Dispatch custDocprops;

    private Dispatch builtInDocProps;

    private Dispatch document;

    private Dispatch wordObject;

    /**
     * 打开word文挡
     */
    public void open(String filename) {
        // 创建一个word对象
        objWord = new ActiveXComponent("Word.Application");
        // 为wordobject组件附值
        wordObject = (Dispatch) (objWord.getObject()); // 改了这里
        // 生成一个只读方式的word文挡组件
        Dispatch.put(wordObject,"Visible", new Variant(false));
        // 获取文挡属性
        Dispatch documents = objWord.getProperty("Documents").toDispatch();
        // 打开激活文挡
        document = Dispatch.call(documents,"Open", filename).toDispatch();
    }

    public void selectCustomDocumentProperitiesMode() {
        custDocprops = Dispatch.get(document,"CustomDocumentProperties").toDispatch();
    }

    public void selectBuiltinPropertiesMode() {
        builtInDocProps = Dispatch.get(document,"BuiltInDocumentProperties").toDispatch();
    }

    /**
     * 关闭文挡
     */
    public void close() {
        if (document != null){
            Dispatch.call(document,"close",false);
        }
        // 关闭文档
//        Dispatch.call(doc, "Close", false);
        // 关闭word应用程序
        objWord.invoke("quit", 0);

    }

    public String getCustomProperty(String cusPropName) {
        try {
            cusPropName = Dispatch.call((Dispatch) custDocprops,"Item", cusPropName).toString();
        } catch (ComException e) {
            cusPropName = null;
        }
        return cusPropName;
    }

    public String getBuiltInProperty(String builtInPropName) {
        try {
            builtInPropName = Dispatch.call((Dispatch) builtInDocProps,"Item", builtInPropName).toString();
        } catch (ComException e) {
            builtInPropName = null;
        }
        return builtInPropName;
    }

    public static void main(String[] args) {
        ActiveComController jacTest = null;
        try {
            jacTest = new ActiveComController();
            jacTest.open("s1.doc");
            jacTest.selectCustomDocumentProperitiesMode();
            jacTest.selectBuiltinPropertiesMode();
            String custValue = jacTest.getCustomProperty("Information Source");
            String builtInValue = jacTest.getBuiltInProperty("Author");
            jacTest.close();
            System.out.println("Document Val One: "+ custValue);
            System.out.println("Document Author: "+ builtInValue);
        } catch (ComFailException e) {
//            System.out.println(e);
            System.out.println(e.toString());
            throw e;
        }
        finally {
            jacTest.close();
        }
    }
}
