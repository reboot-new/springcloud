package com.tan.springcloud2producer.test.pinpin;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.google.gson.JsonObject;

import java.net.PortUnreachableException;

public class SearchResolve {

    public static String PAGE_COUNT_PATH = "$.Head.Summary.Page.PageCount";
    public static String PAGE_INDEX_PATH = "$.Head.Summary.Page.PageIndex";
    public static String PAGE_SIZE_PATH = "$.Head.Summary.Page.PageSize";
    public static String RESULT_COUNT_PATH="$.Head.Summary.ResultCount";
    public static String COUNT_PATH ="$.Paragraph.size()";
    public static String WARES_ARRAY_PATH ="$.Paragraph";

    public static String WARE_IMGURL_PATH = "$.Content.imageurl";
    public static String WARE_NAME_PATH = "$.Content.warename";
    public static String WARE_SKUID_PATH = "$.wareid";

    public static void main(String[] args) {
        String template = "{}爱{}，就像老鼠爱大米";
        System.out.println(StrUtil.format(template, null, "你"));

        FileReader fileReader = new FileReader("D:\\download\\chrome\\FeHelper-20201208103412.json");
        String result = fileReader.readString();
        JSONObject root = JSONObject.parseObject(result);

        System.out.println(Integer.parseInt(JSONPath.eval(root, PAGE_COUNT_PATH).toString()));
        System.out.println(Integer.parseInt(JSONPath.eval(root, PAGE_INDEX_PATH).toString()));
        System.out.println(Integer.parseInt(JSONPath.eval(root, PAGE_SIZE_PATH).toString()));
        System.out.println(Integer.parseInt(JSONPath.eval(root, RESULT_COUNT_PATH).toString()));
        System.out.println(Integer.parseInt(JSONPath.eval(root, COUNT_PATH).toString()));
//        System.out.println(Integer.parseInt(JSONPath.eval(root, "$.Head.Summary.Page.PageCount").toString()));

        JSONArray array = JSONArray.parseArray(JSONPath.eval(root, WARES_ARRAY_PATH).toString());
        if(array == null || array.size() < 1)
            return;
        for (Object obj: array) {
            JSONObject item = (JSONObject)obj;
            System.out.println(JSONPath.eval(item, WARE_SKUID_PATH));
            System.out.println(JSONPath.eval(item, WARE_NAME_PATH));
            System.out.println(JSONPath.eval(item, WARE_IMGURL_PATH));
        }

//        System.out.println(result);
    }
}
