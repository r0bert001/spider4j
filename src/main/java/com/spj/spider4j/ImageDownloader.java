package com.spj.spider4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

@Component
public class ImageDownloader extends SpiderTask implements ConsumerObservable{

    private final Logger logger = LoggerFactory.getLogger(ImageDownloader.class);

    @Value("${spider4j.images.dir}")
    private String imagesDir;

    public ImageDownloader(){
        super();
    }

    @Override
    public boolean doTask(String url) {
        logger.info("开始下载图片：{}",url);
        try {
            HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();
            if(urlConnection.getResponseCode()==200){
                InputStream inputStream =urlConnection.getInputStream();
                FileOutputStream fileOutputStream = new FileOutputStream(String.format("%s\\%s.jpg", getSaveDir(),UUID.randomUUID().toString()));
                byte[] bytes = new byte[1024];
                int len ;
                while ((len = inputStream.read(bytes)) != -1) {

                    fileOutputStream.write(bytes, 0, len);

                }
                inputStream.close();
                fileOutputStream.close();
                return true;
            }else{
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String getSaveDir(){
        File file = new File(imagesDir);
        if(!file.exists()){
            boolean status=file.mkdir();
            if(!status) {
                logger.error("创建文件目录失败！！");
                System.exit(0);
            }
        }
        return file.getAbsolutePath();
    }

}
