package org.jeecg.modules.demo.panorama.util;

import java.util.concurrent.*;

/**
 * @author hiYuzu
 * @version V1.0
 * @date 2022/2/18 14:48
 */
public class ThreadPoolUtil<T> {
    /**
     * 使用无限线程数的CacheThreadPool线程池
     */
    private static final ExecutorService CACHED_THREAD_POOL = Executors.newCachedThreadPool();

    public static <T> T executor(Callable<T> callable) {
        try {
            Future<T> future = CACHED_THREAD_POOL.submit(callable);
            return future.get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}