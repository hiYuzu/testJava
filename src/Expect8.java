import java.util.Random;

/**
 * 知乎看到的问题：
 * 一个计数器，按下一次有50%概率+1，有50%概率-1，平均按下多少次可以使结果为8？
 * <p>
 * 理论
 * 结果非负：数学期望为64
 * 结果可负：理论数学期望不存在或者无穷大
 * <p>
 * 实测
 * 结果非负：10w次测试平均为70次
 * 结果可负：次数波动不确定
 *
 * @author hiYuzu
 * @version V1.0
 * @date 2020/8/25 8:20
 */
public class Expect8 {
    private int max;

    public static void main(String[] args) {
        boolean canNegative = false;
        long times = 0;
        int max = 1000;
        Random random = new Random();
        for (int n = 0; n < max; n++) {
            int num = 0;
            for (long i = 0; ; i++) {
                int t = random.nextInt(10) > 4 ? -1 : 1;
                num += t;
                if (!canNegative && num < 0) {
                    num = 0;
                }
                if (num == 8) {
                    times += i;
                    break;
                }
            }
        }
        System.out.println(times);
        System.out.println(times / max);
    }
}
