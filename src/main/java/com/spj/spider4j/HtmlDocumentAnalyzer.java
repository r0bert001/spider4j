package com.spj.spider4j;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * html分析器
 */
@Component
public class HtmlDocumentAnalyzer {

    private final Logger logger = LoggerFactory.getLogger(HtmlDocumentAnalyzer.class);

    private final
    UrlQueueManager urlQueueManager;

    private final
    BloomFilter bloomFilter;

    public HtmlDocumentAnalyzer(UrlQueueManager urlQueueManager, BloomFilter bloomFilter) {
        this.urlQueueManager = urlQueueManager;
        this.bloomFilter = bloomFilter;
    }

    public void analyzer(String html){
        logger.info("解析html文档");
        Document document = Jsoup.parse(html);
        Elements elements = new Elements();
        Elements aLinks = document.getElementsByTag("a");
        Elements srcLinks = document.getElementsByTag("img");
        logger.info("a标签链接数量：{}",aLinks.size());
        logger.info("img标签链接数量：{}",srcLinks.size());
        elements.addAll(aLinks);
        elements.addAll(srcLinks);
        for (Element element : elements) {
            if(element.hasAttr("href")) preDetectUrl(element.absUrl("href"));
            if(element.hasAttr("src")) preDetectUrl(element.absUrl("src"));
        }
    }

    /**
     * 预检测url
     * @param url 提取到的url
     */
    private void preDetectUrl(String url){
        if(!url.toLowerCase().startsWith("http")) return;
        if(bloomFilter.contains(url))return;
        bloomFilter.add(url);
        urlQueueManager.pushOriginUrl(url);
    }
}
