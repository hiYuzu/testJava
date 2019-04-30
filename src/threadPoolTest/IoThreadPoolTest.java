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
    private static ThreadPoolExecutor cachedThreadPool = (ThreadPoolExecutor) Executors.newCachedThreadPool();

    private static List<Callable<Object>> tasks;

    private static final int TASK_NUM = 5000;

    static {
        tasks = new ArrayList<>(TASK_NUM);
        for (int i = 0; i < TASK_NUM; i++) {
            tasks.add(Executors.callable(new IoTask()));
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
        cachedThreadPool.invokeAll(tasks);
        testExecutor(cachedThreadPool, tasks);
        // 看看执行过程中创建了多少个线程
        int largestPoolSize = cachedThreadPool.getLargestPoolSize();
        System.out.println("largestPoolSize:" + largestPoolSize);
        cachedThreadPool.shutdown();
    }

    private static void testExecutor(ExecutorService executor, List<Callable<Object>> tasks)
            throws InterruptedException {
        long start = System.currentTimeMillis();
        executor.invokeAll(tasks);
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
