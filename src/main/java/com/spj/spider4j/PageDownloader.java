package com.spj.spider4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Component
public class PageDownloader extends SpiderTask{

    private final Logger logger = LoggerFactory.getLogger(PageDownloader.class);

    private final HtmlDocumentAnalyzer htmlDocumentAnalyzer;

    public PageDownloader(HtmlDocumentAnalyzer htmlDocumentAnalyzer){
        super();
        this.htmlDocumentAnalyzer = htmlDocumentAnalyzer;
    }

    @Override
    public boolean doTask(String url) {
        logger.info("开始下载网页：{}",url);
        try {
            HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();
            if(urlConnection.getResponseCode()==200){
                InputStreamReader reader = new InputStreamReader(urlConnection.getInputStream(), StandardCharsets.UTF_8);
                BufferedReader bufferedReader = new BufferedReader(reader);
                StringBuilder stringBuilder = new StringBuilder("");
                String line;
                while((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                reader.close();
                htmlDocumentAnalyzer.analyzer(stringBuilder.toString());
                return stringBuilder.length()>0;
            }else{
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }


}
