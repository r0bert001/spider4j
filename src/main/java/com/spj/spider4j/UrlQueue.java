package com.spj.spider4j;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * url队列实体
 */
public class UrlQueue{

    //阻塞队列
    private BlockingQueue<String> queue;

    public UrlQueue(){
        queue= new ArrayBlockingQueue<>(1000);
    }

    public void push(String url){
        queue.offer(url);
    }

    public String pop(){
        return queue.poll();
    }

    public int getSize(){
        return queue.size();
    }

}
