import cn.hutool.core.thread.ThreadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author hiYuzu
 * @version V1.0
 * @date 2022/3/10 16:07
 */
public class Main {
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        testArrayList();
//        testVector();
    }

    private static void testArrayList() {
        System.out.println("start");
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            list.add(i);
        }
        ThreadUtil.execute(() -> {
            list.removeIf(one -> one >= 50000);
            System.out.println("array1线程执行完毕。数据量：" + list.size());
        });
        ThreadUtil.execute(() -> {
            list.removeIf(one -> one >= 50000);
            System.out.println("array2线程执行完毕。数据量：" + list.size());
        });
        ThreadUtil.execute(() -> {
            list.removeIf(one -> one >= 50000);
            System.out.println("array3线程执行完毕。数据量：" + list.size());
        });
    }

    private static void testVector() {
        System.out.println("start");
        List<Integer> list = new Vector<>();
        for (int i = 0; i < 100000; i++) {
            list.add(i);
        }
        ThreadUtil.execute(() -> {
            list.removeIf(one -> one >= 50000);
            System.out.println("vector1线程执行完毕。数据量：" + list.size());
        });
        ThreadUtil.execute(() -> {
            list.removeIf(one -> one >= 50000);
            System.out.println("vector2线程执行完毕。数据量：" + list.size());
        });
        ThreadUtil.execute(() -> {
            list.removeIf(one -> one >= 50000);
            System.out.println("vector3线程执行完毕。数据量：" + list.size());
        });
    }
}

