package com.tan.springcloud2producer.test;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.thread.ThreadUtil;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Expirations;
import org.ehcache.xml.model.TimeUnit;

import java.time.Duration;
import java.util.AbstractSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class EhcacheTest {


    public static void main(String[] args) throws Exception {
        TimedCache<String, String> timedCache = CacheUtil.newTimedCache(4);
//实例化创建
//TimedCache<String, String> timedCache = new TimedCache<String, String>(4);

        timedCache.put("key1", "value1", 1);//1毫秒过期
        timedCache.put("key2", "value2", DateUnit.SECOND.getMillis() * 5);
        timedCache.put("key3", "value3");//默认过期(4毫秒)

//启动定时任务，每5毫秒秒检查一次过期
        timedCache.schedulePrune(5);

//等待5毫秒
        ThreadUtil.sleep(5);

//5毫秒后由于value2设置了5毫秒过期，因此只有value2被保留下来
        String value1 = timedCache.get("key1");//null
        String value2 = timedCache.get("key2");//value2

//5毫秒后，由于设置了默认过期，key3只被保留4毫秒，因此为null
        String value3 = timedCache.get("key3");//null

//取消定时清理
        timedCache.cancelPruneSchedule();
    }
}
