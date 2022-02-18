package threadPoolTest;

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
    private static final ThreadPoolExecutor CACHED_THREAD_POOL = (ThreadPoolExecutor) Executors.newCachedThreadPool();

    public static <T> Future<T> executor(Callable<T> callable) {
        return CACHED_THREAD_POOL.submit(callable);
    }

    public static <T> T executor(Callable<T> callable, Class<T> clazz) {
        Future<T> future = CACHED_THREAD_POOL.submit(callable);
        try {
            return future.get();
        } catch (Exception e1) {
            try {
                e1.printStackTrace();
                return clazz.getDeclaredConstructor().newInstance();
            } catch (Exception e2) {
                e2.printStackTrace();
                return null;
            }

        }

    }
}