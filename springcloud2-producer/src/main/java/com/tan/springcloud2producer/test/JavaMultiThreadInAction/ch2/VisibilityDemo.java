package com.tan.springcloud2producer.test.JavaMultiThreadInAction.ch2;


import cn.hutool.core.thread.ThreadUtil;

public class VisibilityDemo {

    public static void main(String[] args) throws InterruptedException {
        TimeConsumingTask timeConsumingTask = new TimeConsumingTask();
        Thread thread = new Thread(timeConsumingTask);
        thread.start();

        // 指定的时间内任务没有执行结束的话，就将其取消
        Thread.sleep(10000);
        timeConsumingTask.cancel();
    }
}

class TimeConsumingTask implements Runnable {
    private boolean toCancel = false;

    @Override
    public void run() {
        while (!toCancel) {
            if (doExecute()) {
                break;
            }
        }
        if (toCancel) {
            System.out.println("Task was canceled.");
        } else {
            System.out.println("Task done.");
        }
    }

    private boolean doExecute() {
        boolean isDone = false;
        System.out.println("executing...");

        // 模拟实际操作的时间消耗
        ThreadUtil.sleep(1000*2);
        // 省略其他代码

        return isDone;
    }

    public void cancel() {
        toCancel = true;
        System.out.println(this + " canceled.");
    }
}
