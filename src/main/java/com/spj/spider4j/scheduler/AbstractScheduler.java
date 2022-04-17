package com.spj.spider4j.scheduler;

import com.spj.spider4j.SpiderTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Timer;
import java.util.TimerTask;

public abstract class AbstractScheduler extends TimerTask implements Scheduler {


    private final Logger logger = LoggerFactory.getLogger(AbstractScheduler.class);

    private final SpiderTask spiderTask;

    private final Timer timer;

    public abstract String getUrl();

    public abstract void rollBackUrl(String url);

    public AbstractScheduler(SpiderTask spiderTask){
        this.spiderTask = spiderTask;
        timer = new Timer();
        schedule();
    }


    @Override
    public void run() {
        String url = getUrl();
        if(url!=null) {
            boolean status  = spiderTask.submitTask(url);
            logger.info("下载器执行结果：{}",status?"成功":"失败");
            //执行失败了
            if(!status) rollBackUrl(url);
        };
    }

    @Override
    public void schedule(){
        logger.info("开始调度");
        timer.scheduleAtFixedRate(this,1000,1000);
    }
}
