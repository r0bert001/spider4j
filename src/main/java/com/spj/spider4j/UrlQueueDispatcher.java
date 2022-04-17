package com.spj.spider4j;

import com.spj.spider4j.scheduler.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

@Component
public class UrlQueueDispatcher extends TimerTask implements Scheduler {

    private final Logger logger  = LoggerFactory.getLogger(UrlQueueDispatcher.class);

    final
    UrlQueueManager urlQueueManager;

    final
    BloomFilter bloomFilter;

    public UrlQueueDispatcher(UrlQueueManager urlQueueManager, BloomFilter bloomFilter) {
        this.urlQueueManager = urlQueueManager;
        this.bloomFilter = bloomFilter;
        schedule();
    }


    @Override
    public void schedule() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(this,1000,200);
    }

    @Override
    public void run() {
        String url=this.urlQueueManager.getOriginUrl();
        if(url!=null) {
            detectUrl(url);
        }
    }

    private void detectUrl(String urlStr){
        try {
            if(!urlStr.toLowerCase().startsWith("http")) return;
            logger.info("检测url类型：{}",urlStr);
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            String contentType=connection.getHeaderField("Content-Type");
            logger.info(contentType);
            if(contentType!=null&&contentType.toLowerCase().startsWith("image/")){
                logger.info("当前url类型是：图片");
                urlQueueManager.pushImageUrl(urlStr);
            }

            if(contentType!=null&&contentType.toLowerCase().equals("text/html")){
                logger.info("当前url类型是：网页");
                urlQueueManager.pushPageUrl(urlStr);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
