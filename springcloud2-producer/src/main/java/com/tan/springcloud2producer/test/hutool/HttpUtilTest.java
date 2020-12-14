package com.tan.springcloud2producer.test.hutool;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tan.springcloud2producer.entity.ApplyServicerRequest;
import com.tan.springcloud2producer.entity.ApplyVenderRequest;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class HttpUtilTest {
    public static String PRODUCT_SEARCCH_URL = "http://jxpp.search.jd.local/";
    public static void main(String[] args) {


        System.out.println(JSON.toJSONString(null));

        String str = "{\"name\": \"程慧平\",\n" +
                "\"phone\": \"13628641003\",\n" +
                "\"detail\": \"哈啊哈哈哈\",\n" +
                "\"province\": \"22\",\n" +
                "\"city\": 1962,\n" +
                "\"county\": 39013,\n" +
                "\"town\": 39138,\n" +
                "\"body\": {\"time\":1607775823550,\"signStr\":\"cc859351eafc6fae26afc655d753201129dc4b8a8c6981ff5a12b4e3b7e466a8\"},\n" +
                "\"channel\": \"wxappjxpp\",\n" +
                "\"cv\": \"1.0.0\"}";

        ApplyServicerRequest v= JSONObject.parseObject(str, ApplyServicerRequest.class);


        BigDecimal p = new BigDecimal("109.4");
        BigDecimal p1 = new BigDecimal(0.387);
//        skuInfo.setPercentage(MoneyUtil.getShowPrice(p.multiply(p1)));
        System.out.println(p.multiply(p1));

        int i = 5;
        int j = 2;
        int c = i/j;
        System.out.println(c);

        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("key", "测试");
        parmMap.put("pagesize", "20");
        parmMap.put("page", "1");
        parmMap.put("area_ids","1,72,2799,0");
        parmMap.put("warehouse_id", "123");
        parmMap.put("delivery_id", "1113");
        parmMap.put("site_id", "123");
        parmMap.put("leader_id", "123");
        parmMap.put("client", "1607347112371");
        parmMap.put("charset", "utf8");
        parmMap.put("urlencode", "no");
//        parmMap.put("pvid", pvId == null ? "" : pvId);
//        parmMap.put("logid", PfinderContext.getInstance().traceId());
//            filt_onsale=yes
//        parmMap.put("filt_onsale", "yes");
//        parmMap.put("filt_type", "sale_state_filter "); //过滤掉不可售的商品
        String res = HttpUtil.get(PRODUCT_SEARCCH_URL, parmMap);
        System.out.println(res);
    }
}
