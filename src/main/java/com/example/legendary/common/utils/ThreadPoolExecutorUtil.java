package com.example.legendary.common.utils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池工具类
 * @Author: 吴嘉晟
 * @Version: 1.0
 */
public class ThreadPoolExecutorUtil {
    /**
     * 线程池中所存放的线程数
     */
    private static final int CORE_POOL_SIZE = 10;
    /**
     * 线程池中允许的最大线程数
     */
    private static final int MAX_CORE_POOL_SIZE = 1500;
    /**
     * 当线程数大于核心时,终止前多余的空闲线程等待时间(单位：毫秒)
     */
    private static final int KEEP_ALIVE_TIME = 1;
    /**
     * 执行前用于保持任务的队列
     */
    private static final int BLOCKING_QUEUE_SIZE = 1;
    /**
     * 参数的时间单位
     */
    private static final TimeUnit UNIT = TimeUnit.MILLISECONDS;

    private static final ThreadPoolExecutor THREAD_POOL = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_CORE_POOL_SIZE, KEEP_ALIVE_TIME, UNIT, new ArrayBlockingQueue<>(BLOCKING_QUEUE_SIZE), new ThreadPoolExecutor.DiscardOldestPolicy());

    private ThreadPoolExecutorUtil() {}

    public static ThreadPoolExecutor getThreadPool() {
        return THREAD_POOL;
    }

    public static void close() {
        THREAD_POOL.allowCoreThreadTimeOut(true);
    }
}
