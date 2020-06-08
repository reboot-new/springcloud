package com.tan.springcloud2producer.test;

        import cn.hutool.core.date.DateUtil;
        import org.slf4j.Logger;
        import org.slf4j.LoggerFactory;

        import java.util.Calendar;
        import java.util.Date;

public class LoggerTest {

    public static Logger logger = LoggerFactory.getLogger(LoggerTest.class);

    public static void main(String[] args) {

        String dateStr = "2020-01-01";
        Date start = DateUtil.parse(dateStr);

        String edateStr = "2020-05-28";
        Date end = DateUtil.parse(edateStr);
        Calendar c = Calendar.getInstance();

        while (start.compareTo(end) != 1){
            String s = "{\"code\":\"hydroRainCustomTime\",\"intv\":\"24\",\"processParam\":\"{cols:100,rows:100}\",\"tm\":\""+DateUtil.format(start, "yyyy-MM-dd")+"\"}";
            System.out.println(s);
            c.setTime(start);
            c.add(Calendar.DAY_OF_MONTH, 1);
            start =c.getTime();
        }

        String message = null;

        try {
            message.toString();
        }catch (Exception ex){
            logger.error("测试空指针异常",ex);
        }
    }
}
