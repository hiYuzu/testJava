/**
 * <p>
 *
 * @author hiYuzu
 * @version V1.0
 * </p>
 * @description interesting thread program
 * @date 2019/2/15 14:20
 */
public class SleepSort {
    public static void main(String[] args) {
        int[] ints = {1, 4, 7, 3, 8, 9, 2, 6, 5};
        SortThread[] sortThreads = new SortThread[ints.length];
        for (int i = 0; i < sortThreads.length; i++) {
            sortThreads[i] = new SortThread(ints[i]);
        }
        for (int i = 0; i < sortThreads.length; i++) {
            sortThreads[i].start();
        }
    }
}

class SortThread extends Thread {
    int ms;
    public SortThread(int ms) {
        this.ms = ms;
    }
    @Override
    public void run() {
        try {
            System.out.println(ms + "号选手开始睡觉");
            sleep(ms * 10 + 10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(ms);
    }
}
