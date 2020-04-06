package com.tan.springcloud2producer.helper;

import com.alibaba.fastjson.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tan_alpha
 */
public class JsonHelper {

    /**
     * 格式化request中body字符串
     *
     * @param paramStr eg：a=a1&b=b1&c=c1
     * @return eg:{"a":"a1","b":"b1","c":"c1"}
     */
    public static String pareByUrl(String paramStr){
        String[] params = paramStr.split("&");
        JSONObject obj = new JSONObject();
        for (int i = 0; i < params.length; i++) {
            String[] param = params[i].split("=");
            if (param.length >= 2) {
                String key = param[0];
                String value = param[1];
                for (int j = 2; j < param.length; j++) {
                    value += "=" + param[j];
                }
                try {
                    obj.put(key,value);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return obj.toString();
    }

    /**
     * 计算指定路径下的json对象值
     *
     * @param text json字符串
     * @param path 计算路径
     * @return 计算结果
     * @author tan_alpha
     * @date 2019/5/5 16:31
     **/
    public static Object evalByPath(String text,String path){
        /*
        * 1、判断是jsonarray还是jsonobject字符串
        * 2、转换字符串
        * 3、判断路径key是否存在
        * 4、根据路径计算结果
        * */
        JSON json = null;
        if(text.matches("(?m)^\\[.*\\]$")) {
            //类型 jsonarray
            json = JSONArray.parseArray(text);
        }else {
            json = JSONObject.parseObject(text);
        }
        return JSONPath.eval(json,path);
    }

    public static void test(){
        String jsonStr = "{ \"store\": {\"book\": [{ \"category\": \"reference\","+
                "\"author\": \"Nigel Rees\",\"title\": \"Sayings of the Century\","+
                "\"price\": 8.95},{ \"category\": \"fiction\",\"author\": \"Evelyn Waugh\","+
                "\"title\": \"Sword of Honour\",\"price\": 12.99,\"isbn\": \"0-553-21311-3\""+
                "}],\"bicycle\": {\"color\": \"red\",\"price\": 19.95}}}";
        JSONObject jsonObject = JSON.parseObject(jsonStr);

        System.out.println("Book数目：" + JSONPath.eval(jsonObject, "$.store.book.size()"));
        System.out.println("第一本书title：" + JSONPath.eval(jsonObject, "$.store.book[0].title"));
        System.out.println("price大于10元的book：" + JSONPath.eval(jsonObject, "$.store.book[price > 10]"));
        System.out.println("price大于10元的title：" + JSONPath.eval(jsonObject, "$.store.book[price > 10][0].title"));
        System.out.println("category(类别)为fiction(小说)的book：" + JSONPath.eval(jsonObject, "$.store.book[category = 'fiction']"));
        System.out.println("bicycle的所有属性值" + JSONPath.eval(jsonObject, "$.store.bicycle.*"));
        System.out.println("bicycle的color和price属性值" + JSONPath.eval(jsonObject, "$.store.bicycle['color','price']"));
    }


    public static void main(String[] args) {
        ArrayList<String> lst = new ArrayList<>();
        lst.add("111");
        lst.add("222");
        lst.add("333");
        lst.add("444");
        String str2 =  JSON.toJSONString(lst);
        List<String> lst1 = JSON.parseArray(str2,String.class);

        String str = "[{\"birth\":\"2019-05-05T02:44:48.563+0000\",\"name\":\"hello1\",\"age\":1,\"height\":1.1},{\"birth\":\"2019-05-05T02:44:48.563+0000\",\"name\":\"hello2\",\"age\":2,\"height\":2.2}]";
        String jsonStr = "{ \"store\": {\"book\": [{ \"category\": \"reference\","+
                "\"author\": \"Nigel Rees\",\"title\": \"Sayings of the Century\","+
                "\"price\": 8.95},{ \"category\": \"fiction\",\"author\": \"Evelyn Waugh\","+
                "\"title\": \"Sword of Honour\",\"price\": 12.99,\"isbn\": \"0-553-21311-3\""+
                "}],\"bicycle\": {\"color\": \"red\",\"price\": 19.95}}}";
        JSONObject jobj =  (JSONObject) JSONObject.parse(jsonStr);
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("x",jobj.getString("store1"));
        map.put("y","43");
        list.add(map);
        System.out.println(JSON.toJSONString(list));
//        Object obj =  evalByPath(str,"$[0:].name");
//        System.out.println(obj);
//
//        Object obj2 =  evalByPath(jsonStr,"$.store.book[0:].title");
//        System.out.println(obj2);
    }
}