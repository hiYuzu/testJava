import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author hiYuzu
 * @version V1.0
 * @date 2020/11/19 14:58
 */
public class ThreadPoolUtil {

    private ThreadPoolUtil() {
    }

    private static class SingletonHolder {
        private static final ThreadPoolUtil INSTANCE = new ThreadPoolUtil();
    }

    public static ThreadPoolUtil getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private final int CORE_POOL_SIZE = 4;

    private final ThreadFactory scheduleThreadFactory  = new ThreadFactory() {
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "ScheduleThread-" + threadNumber.getAndIncrement());
        }
    };

    private final ScheduledExecutorService SCHEDULED_EXECUTOR_SERVICE = new ScheduledThreadPoolExecutor(CORE_POOL_SIZE, scheduleThreadFactory);
    /**
     * 定时任务型线程创建
     *
     * @param runnable 线程执行体
     * @param initialDelay    延迟时间
     * @param period 周期
     * @param unit 延迟时间单位
     * @throws NullPointerException 当 runnable 为 null 时，抛出
     * @see ScheduledExecutorService
     */
    public Future scheduledCommonExecute(Runnable runnable, long initialDelay, long period, TimeUnit unit) throws NullPointerException {
        return SCHEDULED_EXECUTOR_SERVICE.scheduleAtFixedRate(runnable, initialDelay, period, unit);
    }

}