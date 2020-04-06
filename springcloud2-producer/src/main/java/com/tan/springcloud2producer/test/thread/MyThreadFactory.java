package com.tan.springcloud2producer.test.thread;

import java.util.concurrent.ThreadFactory;

public class MyThreadFactory implements ThreadFactory {

    @Override
    public Thread newThread(Runnable r) {
        return null;
    }
}
