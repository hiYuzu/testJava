import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;

/**
 * @author hiYuzu
 * @version V1.0
 * @date 2021/1/7 11:52
 */
public class BlockingTest {
    private static final LinkedBlockingQueue<String> LINKED_BLOCKING_QUEUE = new LinkedBlockingQueue<>();
    private static int i = 1;
    private static long oldTime;

    public static void main(String[] args) {
        LINKED_BLOCKING_QUEUE.add("start");
        ScheduledExecutorService service = new ScheduledThreadPoolExecutor(2, (ThreadFactory) Thread::new);
        service.scheduleAtFixedRate(BlockingTest::putStr, 0, 1000, TimeUnit.MILLISECONDS);
        oldTime = System.currentTimeMillis();
        service.scheduleAtFixedRate(BlockingTest::takeStr, 0, 500, TimeUnit.MILLISECONDS);
    }

    private static void putStr() {
        try {
            LINKED_BLOCKING_QUEUE.put("content:" + i);
            i++;
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }

    private static void takeStr() {
        try {
            System.out.println(LINKED_BLOCKING_QUEUE.take());
            long newTime = System.currentTimeMillis();
            System.out.println(newTime - oldTime);
            oldTime = newTime;
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }
}
