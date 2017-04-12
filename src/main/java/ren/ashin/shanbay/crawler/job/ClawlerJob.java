package ren.ashin.shanbay.crawler.job;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @ClassName: ClawlerJob
 * @Description: 定时爬取数据job
 * @author renzx
 * @date Apr 12, 2017
 */
public class ClawlerJob implements Job {
    private static final Logger LOG = Logger.getLogger(ClawlerJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        Date fireTime = context.getFireTime();
        exeTask(fireTime);
    }

    private void exeTask(Date fireTime) {
        LOG.info("定时爬取任务开始执行，执行时间为："+fireTime.toString());
    }


}
