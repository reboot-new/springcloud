package com.tan.springcloud2producer.test.hutool;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.base.Optional;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

public class StrUtilTest {
    public static void main(String[] args) {

        Optional<Integer> o = Optional.absent();
        System.out.println(o.isPresent());


        Boolean b = null;
        if(BooleanUtil.isTrue(b)){
            System.out.println("343");
        }else {
            System.out.println("12");
        }

        Integer i = 3;
        DecimalFormat df = new DecimalFormat();
        df.applyPattern("##");
        double d = 1.5;
        System.out.println(df.format(d));
        System.out.println(df.getRoundingMode());
    }

    public static void testTrim(){
        String data = "fdssfnifsdai,fdasfdsafdsaf,SFDSAFSAD,";
        System.out.println(StrUtil.removeSuffixIgnoreCase(data,"SFDsafsad,"));


        Map<String,String> map = new HashMap<>();
        map.put("1","342432");

        Integer it = MapUtils.getInteger(map,"1");
        System.out.println(it);

        System.out.println(MapUtils.getString(map, "4554"));
        System.out.println(MapUtil.getStr(map,"33"));
    }
}
