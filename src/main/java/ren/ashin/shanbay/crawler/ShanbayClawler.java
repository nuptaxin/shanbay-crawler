package ren.ashin.shanbay.crawler;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import org.aeonbits.owner.ConfigFactory;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ren.ashin.shanbay.crawler.job.ClawlerJob;
import ren.ashin.shanbay.crawler.job.ScanUserJob;
import ren.ashin.shanbay.crawler.util.MainConfig;

/**
 * @ClassName: ShanbayClawler
 * @Description: 程序入口
 * @author renzx
 * @date Apr 12, 2017
 */
public class ShanbayClawler {
    /**
     * @Fields LOG : TODO
     */
    private static final Logger LOG = Logger.getLogger(ShanbayClawler.class);
    public static Scheduler scheduler = null;
    public static ApplicationContext ctx = null;

    public static JobKey jobKey = null;
    public static JobKey userKey = null;

    public static MainConfig mfg = null;

    public static void main(String[] args) {
        PropertyConfigurator.configure("conf/log4j-shanbay.properties");
        ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        mfg = ConfigFactory.create(MainConfig.class);

        try {
            initScheduler();
        } catch (SchedulerException e) {
            LOG.error("初始化定时器出错", e);
        }
    }

    public static void initScheduler() throws SchedulerException {
        // 调度任务工厂
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        scheduler = schedulerFactory.getScheduler();

        // 定时任务
        JobDetail scanJobDetail = newJob(ClawlerJob.class).withIdentity("定时任务", "group1").build();
        jobKey = scanJobDetail.getKey();
        CronTrigger scanTrigger =
                newTrigger().withIdentity("trigger1", "group1")
                        .withSchedule(cronSchedule(mfg.cronTask())).build();
        scheduler.scheduleJob(scanJobDetail, scanTrigger);

        // 用户表扫描任务
        JobDetail scanUserDetail =
                newJob(ScanUserJob.class).withIdentity("用户表扫描", "group1").build();
        userKey = scanUserDetail.getKey();
        CronTrigger scanUserTrigger =
                newTrigger().withIdentity("trigger2", "group1")
                        .withSchedule(cronSchedule(mfg.cronUser())).build();
        scheduler.scheduleJob(scanUserDetail, scanUserTrigger);

        scheduler.start();
        // 程序启动后立即触发一次
        // scheduler.triggerJob(jobKey);
        // scheduler.triggerJob(userKey);
    }
}
