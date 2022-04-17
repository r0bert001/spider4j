package com.spj.spider4j;

/**
 * 消费者观测接口
 */
public interface ConsumerObservable {
    /**
     * 检测处理程序是否处理繁忙
     * @return boolean
     */
    boolean observeBusy();

}
