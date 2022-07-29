package threadPoolTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author hiYuzu
 * @version V1.0
 * @date 2019/4/30 14:36
 */
public class IoThreadPoolTest {
    /**
     * 使用无限线程数的CacheThreadPool线程池
     */
    private static final ThreadPoolExecutor CACHED_THREAD_POOL = (ThreadPoolExecutor) Executors.newCachedThreadPool();

    private static final List<Callable<Object>> TASKS;

    private static final int TASK_NUM = 5000;

    static {
        TASKS = new ArrayList<>(TASK_NUM);
        for (int i = 0; i < TASK_NUM; i++) {
            TASKS.add(Executors.callable(new IoTask()));
        }
    }

    static class IoTask implements Runnable {
        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // warm up all thread
        long start = System.currentTimeMillis();
        CACHED_THREAD_POOL.invokeAll(TASKS);
        System.out.println("warm up：" + (System.currentTimeMillis() - start));
        testExecutor();
        // 看看执行过程中创建了多少个线程
        int largestPoolSize = CACHED_THREAD_POOL.getLargestPoolSize();
        System.out.println("largestPoolSize:" + largestPoolSize);
        CACHED_THREAD_POOL.shutdown();
    }

    private static void testExecutor() throws InterruptedException {
        long start = System.currentTimeMillis();
        CACHED_THREAD_POOL.invokeAll(TASKS);
        System.out.println("invoke时间：" + (System.currentTimeMillis() - start));
    }
}
