package com.spj.spider4j.scheduler;

import com.spj.spider4j.ImageDownloader;
import com.spj.spider4j.SpiderTask;
import com.spj.spider4j.UrlQueueManager;
import com.spj.spider4j.scheduler.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Timer;
import java.util.TimerTask;

@Component
public class ImageDownloadScheduler extends AbstractScheduler {

    private final Logger logger = LoggerFactory.getLogger(ImageDownloadScheduler.class);

    private final
    UrlQueueManager urlQueueManager;

    public ImageDownloadScheduler(ImageDownloader imageDownloader, UrlQueueManager urlQueueManager) {
        super(imageDownloader);
        this.urlQueueManager = urlQueueManager;
    }

    @Override
    public String getUrl() {
        return urlQueueManager.getImageUrl();
    }

    @Override
    public void rollBackUrl(String url) {
        logger.info("图片下载任务执行失败，重入队列：{}",url);
        urlQueueManager.pushImageUrl(url);
    }
}
