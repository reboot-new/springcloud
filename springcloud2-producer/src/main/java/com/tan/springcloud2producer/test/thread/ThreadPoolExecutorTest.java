package com.tan.springcloud2producer.test.thread;


import cn.hutool.core.thread.ThreadUtil;
import com.tan.springcloud2producer.test.thread.MyTask1;

import java.util.concurrent.*;

import static java.util.concurrent.TimeUnit.SECONDS;

public class ThreadPoolExecutorTest {

        public static void main(String[] args) {
            BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>();
            ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1,1,1000L, SECONDS,workQueue);
            Runnable r =  new MyTask1("hello");
            threadPoolExecutor.execute(r);
            ThreadUtil.sleep(1000*2);
            Runnable r1 =  new MyTask2("hello2");
            threadPoolExecutor.execute(r1);
            Executors.newFixedThreadPool(2);
            ThreadUtil.sleep(1000 * 100);

    }
}
