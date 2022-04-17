package com.spj.spider4j;

import java.util.concurrent.*;

public abstract class SpiderTask implements ConsumerObservable{

    private final ExecutorService executorService;

    private final int MAX_WORK_THREADS = 3;


    public SpiderTask(){
        executorService = Executors.newFixedThreadPool(MAX_WORK_THREADS);
    }

    public abstract boolean doTask(String url);

    @Override
    public boolean observeBusy() {
        return ((ThreadPoolExecutor)this.executorService).getActiveCount()== MAX_WORK_THREADS;
    }


    public boolean submitTask(String url){
        if(url==null) return false;
        try {
            Future<Boolean> future= executorService.submit(()->this.doTask(url));
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return false;
        }
    }
}
