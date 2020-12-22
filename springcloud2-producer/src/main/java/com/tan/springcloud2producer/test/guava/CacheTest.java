package com.tan.springcloud2producer.test.guava;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.graph.Graph;
import com.google.common.graph.Graphs;
import com.google.inject.Key;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class CacheTest {
    public static void main(String[] args) {
        LoadingCache<Integer, String> cache = CacheBuilder.newBuilder().build(
                new CacheLoader<Integer, String>() {
                    @Override
                    public String load(Integer key) throws Exception{
                        System.out.println("后台加载");
                        String str = null;
                        str.toString();
                        return "key-" + key;
                    }
                }
        );
        cache.put(1, "a");
        System.out.println(cache.getUnchecked(2));
        System.out.println(cache.getIfPresent(1));
        try {
            System.out.println(cache.get(3));
            System.out.println(cache.get(2));
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static Graph createExpensiveGraph(Key key){

        return null;
    }
}
