package threadPoolTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author hiYuzu
 * @version V1.0
 * @date 2019/4/30 15:07
 */
public class ComputeThreadPoolTest {

    private static ThreadPoolExecutor computeExecutor;

    private static List<Callable<Long>> computeTasks;

    private static final int TASK_NUM = 5000;

    private static final int MULTIPLE = 3;

    private static final int AVG_TIMES = 8;

    static {
        computeExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
        // 创建5000个计算任务
        computeTasks = new ArrayList<>(TASK_NUM);
        for (int i = 0; i < TASK_NUM; i++) {
            computeTasks.add(new ComputeTask());
        }
    }

    static class ComputeTask implements Callable<Long> {
        final int COUNT = 500000;

        /**
         * 计算一至五十万数的总和(纯计算任务)
         *
         * @return sum
         */
        @Override
        public Long call() {
            long sum = 0;
            for (long i = 0; i < COUNT; i++) {
                sum += i;
            }
            return sum;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // 获取电脑线程数
        int processorsCount = Runtime.getRuntime().availableProcessors();
        // 逐一增加线程池的线程数,最大是电脑总线程数的三倍
        for (int i = 1; i <= processorsCount * MULTIPLE; i++) {
            computeExecutor.setCorePoolSize(i);
            computeExecutor.setMaximumPoolSize(i);
            computeExecutor.prestartAllCoreThreads();
            System.out.print(i);
            // warm up all thread
            computeExecutor.invokeAll(computeTasks);
            System.out.print("\t");
            testExecutor(computeExecutor, computeTasks);
            System.out.println();
            // 一定要让cpu休息会儿，Windows桌面操作系统不会让应用长时间霸占CPU
            // 否则Windows回收应用程序的CPU核心数将会导致测试结果不准确
            TimeUnit.SECONDS.sleep(5);
        }
        computeExecutor.shutdown();
    }

    private static <T> void testExecutor(ExecutorService executor, List<Callable<T>> tasks)
            throws InterruptedException {
        long sum = 0;
        for (int i = 0; i < AVG_TIMES; i++) {
            long start = System.currentTimeMillis();
            // ignore result
            executor.invokeAll(tasks);
            long end = System.currentTimeMillis();
            // 记录时间间隔
            long time = end - start;
            sum += time;
            System.out.print(time);
            System.out.print("\t");
            // cpu rest
            TimeUnit.SECONDS.sleep(1);
            if (i + 1 == AVG_TIMES) {
                System.out.println("avg time = " + sum / AVG_TIMES);
            }
        }
    }
}
