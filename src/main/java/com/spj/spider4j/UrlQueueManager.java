package com.spj.spider4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Timer;
import java.util.TimerTask;

@Component
public class UrlQueueManager extends TimerTask {

    private final Logger logger = LoggerFactory.getLogger(UrlQueueManager.class);

    /**
     * 原始url队列
     */
    private final UrlQueue originUrlQueue;

    /**
     * 图片url队列
     */
    private final UrlQueue imageQueue;

    /**
     * 网页url队列
     */
    private final UrlQueue pageUrlQueue;


    public UrlQueueManager( @Value("${spider4j.seed.url}") String targetUrl){
        logger.info("创建队列管理器");
        logger.info("初始化图片url队列");
        this.imageQueue = new UrlQueue();
        logger.info("初始化网页url队列");
        this.pageUrlQueue = new UrlQueue();
        logger.info("初始化原始url队列");
        this.originUrlQueue = new UrlQueue();
        pushOriginUrl(targetUrl);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(this,1000,3000);
    }

    /**
     * 图片url入队列
     * @param targetUrl 目标url
     */
    public void pushImageUrl(String targetUrl){
        this.imageQueue.push(targetUrl);
    }

    /**
     * 网页url入队列
     * @param targetUrl 目标url
     */
    public void pushPageUrl(String targetUrl){
        this.pageUrlQueue.push(targetUrl);
    }

    /**
     * 图片url出队列
     * @return 图片url
     */
    public String getImageUrl(){
        return this.imageQueue.pop();
    }

    /**
     * 网页url出队列
     * @return 网页url
     */
    public String getPageUrl(){
        return this.pageUrlQueue.pop();
    }

    /**
     * 原始url出队列
     * @return 原始url
     */
    public String getOriginUrl(){
        return this.originUrlQueue.pop();
    }

    /**
     * 原始url入队列
     * @param targetUrl 原始url
     */
    public void pushOriginUrl(String targetUrl){
        this.originUrlQueue.push(targetUrl);
    }


    @Override
    public void run() {
        logger.info("原始url队列长度：{}，图片url队列长度：{}，网页url队列长度：{}",this.originUrlQueue.getSize(),this.imageQueue.getSize(),this.pageUrlQueue.getSize());
    }
}
