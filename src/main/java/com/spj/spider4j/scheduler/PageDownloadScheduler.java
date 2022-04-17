package com.spj.spider4j.scheduler;

import com.spj.spider4j.PageDownloader;
import com.spj.spider4j.UrlQueueManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class PageDownloadScheduler extends AbstractScheduler {

    private final Logger logger = LoggerFactory.getLogger(PageDownloadScheduler.class);

    private final
    UrlQueueManager urlQueueManager;


    public PageDownloadScheduler(PageDownloader pageDownloader, UrlQueueManager urlQueueManager) {
        super(pageDownloader);
        this.urlQueueManager = urlQueueManager;
    }

    @Override
    public String getUrl() {
        return urlQueueManager.getPageUrl();
    }

    @Override
    public void rollBackUrl(String url) {
        logger.info("网页下载任务执行失败，重入队列：{}",url);
        urlQueueManager.pushPageUrl(url);
    }
}
