package threadPoolTest;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 并发量测试
 *
 * @author hiYuzu
 * @version V1.0
 * @date 2022/7/29 14:25
 */
public class ConcurrencyTest {
    private static final ThreadPoolExecutor CACHED_THREAD_POOL = new ThreadPoolExecutor(0,
            Integer.MAX_VALUE,
            60L,
            TimeUnit.SECONDS,
            new SynchronousQueue<>(),
            new ThreadFactoryBuilder().build());

    private final List<Callable<Object>> tasks;

    /**
     * 实例化对象
     *
     * @param taskNum 任务数量
     * @param task {@link Runnable#run()} 任务详情
     */
    public ConcurrencyTest(int taskNum, Runnable task) {
        this.tasks = new ArrayList<>(taskNum);
        for (int i = 0; i < taskNum; i++) {
            tasks.add(Executors.callable(task));
        }
    }

    public int execute() throws Exception {
        CACHED_THREAD_POOL.invokeAll(tasks);
        int largestPoolSize = CACHED_THREAD_POOL.getLargestPoolSize();
        CACHED_THREAD_POOL.shutdown();
        return largestPoolSize;
    }
}
