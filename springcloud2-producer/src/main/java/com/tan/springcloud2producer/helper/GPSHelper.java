package com.tan.springcloud2producer.helper;

import java.util.regex.Pattern;

public class GPSHelper {
    public static boolean testGPS(String longitude ,String latitude){
        //经度： -180.0～+180.0（整数部分为0～180，必须输入1到8位小数）
        String longitudePattern="^[\\-\\+]?(0(\\.\\d{1,10})?|([1-9](\\d)?)(\\.\\d{1,10})?|1[0-7]\\d{1}(\\.\\d{1,10})?|180\\.0{1,10})$";
        //纬度： -90.0～+90.0（整数部分为0～90，必须输入0到10位小数）
        String latitudePattern="^[\\-\\+]?((0|([1-8]\\d?))(\\.\\d{1,10})?|90(\\.0{1,10})?)$";
        boolean longitudeMatch = Pattern.matches(longitudePattern, longitude);
        boolean latitudeMatch = Pattern.matches(latitudePattern, latitude);
        if(longitudeMatch&&latitudeMatch){
            return true;
        }
        return false;
    }
}
