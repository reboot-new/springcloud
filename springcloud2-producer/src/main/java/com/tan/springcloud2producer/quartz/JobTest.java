package com.tan.springcloud2producer.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JobTest implements Job {

    private int count = 1;

    private Logger logger = LoggerFactory.getLogger(JobTest.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("测试quartz  Job定时任务！！>>>>>>>>>>"+count++);
    }
}
