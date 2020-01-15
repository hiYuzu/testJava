package kyqtask;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author hiYuzu
 * @version V1.0
 * @date 2019/12/11 8:47
 */
public class Problem1Thread {
    private static ThreadPoolExecutor computeExecutor;
    private static List<Callable<String>> computeTasks;

    static {
        computeExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
        ArrayList<String> arrayList = Problem1File.readFileByLine();
        computeTasks = new ArrayList<>(arrayList.size());
        for (String str : arrayList) {
            computeTasks.add(new ComputeTask(str));
        }
    }

    private static class ComputeTask implements Callable<String> {
        private String str;
        ComputeTask(String str) {
            this.str = str;
        }

        @Override
        public String call() {
            int num = 0;
            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);
                if (c == 'b') {
                    num++;
                }
            }
            if (num % 2 == 1) {
                System.out.println(str + "：yes");
            } else {
                System.out.println(str + "：no");
            }
            return null;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // 获取电脑线程数
        int processorsCount = Runtime.getRuntime().availableProcessors();
        computeExecutor.setCorePoolSize(processorsCount);
        computeExecutor.setMaximumPoolSize(processorsCount);
        computeExecutor.prestartAllCoreThreads();
        computeExecutor.invokeAll(computeTasks);
        computeExecutor.shutdown();
    }
}
