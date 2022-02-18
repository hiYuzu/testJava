package threadPoolTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

/**
 * @author hiYuzu
 * @version V1.0
 * @date 2022/2/18 16:26
 */
public class ThreadPoolMain {
    public static void main(String[] args) {
        nonBlockMain();
        blockMain();
    }

    public static void nonBlockMain() {
        List<Future<?>> futures = new ArrayList<>();
        Future<?> future1 = ThreadPoolUtil.executor(() -> {
            ThreadPoolMain.Test temp = new ThreadPoolMain.Test();
            temp.setId("1");
            temp.setName("test1");
            Thread.sleep(5000);
            return temp;
        });
        futures.add(future1);
        Future<?> future2 = ThreadPoolUtil.executor(() -> {
            ThreadPoolMain.Test temp = new ThreadPoolMain.Test();
            temp.setId("2");
            temp.setName("test2");
            Thread.sleep(5000);
            return temp;
        });
        futures.add(future2);
        System.out.println("两个线程并行处理，不阻塞主线程");
        for (Future<?> future : futures) {
            try {
                System.out.println(future.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void blockMain() {
        Test test1 = ThreadPoolUtil.executor(() -> {
            ThreadPoolMain.Test temp = new ThreadPoolMain.Test();
            temp.setId("1");
            temp.setName("test1");
            Thread.sleep(5000);
            return temp;
        }, Test.class);

        Test test2 = ThreadPoolUtil.executor(() -> {
            ThreadPoolMain.Test temp = new ThreadPoolMain.Test();
            temp.setId("2");
            temp.setName("test2");
            Thread.sleep(5000);
            return temp;
        }, Test.class);

        System.out.println("两个线程顺序处理，阻塞主线程");
        System.out.println(test1);
        System.out.println(test2);
    }

    public static class Test {
        String id;
        String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "{\"id\": " + this.getId() + ", \"name\": " + this.getName() + "}";
        }
    }
}
