import java.util.Random;

/**
 * <p>
 * 灵感来源：<br>
 * 在电子游戏中99%的暴击率、1%的爆伤和1%的暴击率、99%的爆伤，两者谁带来的收益更高？
 * </p>
 *
 * @author hiYuzu
 * @version V1.0
 * @date 2021/9/18 16:31
 * @see <a href="https://www.zhihu.com/question/436314102/answer/1650145729">知乎</a>
 */
public class Fight {
    // 先手控制
    private boolean aFirst = true;
    // 血量
    private double bloodA = 10000D;
    private double bloodB = 10000D;
    // 测试次数
    private int battleTimes = 1000;
    // 获胜次数
    private int aWinTimes = 0;
    private int bWinTimes = 0;
    private int peaceTimes =0;

    // 死亡回击
    private boolean deadAttack = true;

    private final Random random = new Random();

    public Fight() {
    }

    /**
     * 输入初始参数
     *
     * @param aFirst 先手控制：true -> A 先手
     * @param bloodA A 血量
     * @param bloodB B 血量
     * @param battleTimes 测试次数
     */
    public Fight(boolean aFirst, double bloodA, double bloodB, int battleTimes, boolean deadAttack) {
        this.aFirst = aFirst;
        this.bloodA = bloodA;
        this.bloodB = bloodB;
        this.battleTimes = battleTimes;
        this.deadAttack = deadAttack;
    }

    public void startBattle() {
        int cacheTimes = battleTimes;
        double cacheBloodA = bloodA;
        double cacheBloodB = bloodB;

        while (battleTimes > 0) {
            int roundCount = 1;
            if (aFirst) {
                while (bloodA > 0 && bloodB > 0) {
                    System.out.println("Round " + roundCount);
                    aAttackB();
                    if (bloodB > 0) {
                        bAttackA();
                    } else if (deadAttack) {
                        bAttackA();
                    }
                    roundCount++;
                }
            } else {
                while (bloodA > 0 && bloodB > 0) {
                    System.out.println("Round " + roundCount);
                    bAttackA();
                    if (bloodA > 0) {
                        aAttackB();
                    } else if (deadAttack) {
                        aAttackB();
                    }
                    roundCount++;
                }
            }
            if (bloodA > 0) {
                System.out.println("游戏结束，胜者为A，剩余血量" + bloodA);
                aWinTimes++;
            } else if (bloodB > 0) {
                System.out.println("游戏结束，胜者为B，剩余血量" + bloodB);
                bWinTimes++;
            } else {
                System.out.println("游戏结束，平局");
                peaceTimes++;
            }
            battleTimes--;
            bloodA = cacheBloodA;
            bloodB = cacheBloodB;
        }
        System.out.println("测试结束，在A先手的 " + cacheTimes +" 次模拟中，A获胜 " + aWinTimes + " 次，B获胜 " + bWinTimes + " 次，平局 " + peaceTimes + " 次");
    }

    private void aAttackB() {
        // 基础伤害
        double damageA = 10D;
        // 暴击率 0.0 ~ 1.0
        double criticalA = 0.99D;
        if (random.nextDouble() <= criticalA) {
            // 爆伤倍率
            double magA = 1.01D;
            damageA = damageA * magA;
        }
        bloodB -= damageA;
        System.out.println("A 对 B 造成了 " + damageA + " 点伤害。");
        System.out.println("A 剩余 " + bloodA + " 点生命。");
        System.out.println("B 剩余 " + bloodB + " 点生命。");
    }

    private void bAttackA() {
        double damageB = 10D;
        double criticalB = 0.01D;
        if (random.nextDouble() <= criticalB) {
            double magB = 1.99D;
            damageB = damageB * magB;
        }
        bloodA -= damageB;
        System.out.println("B 对 A 造成了 " + damageB + " 点伤害。");
        System.out.println("A 剩余 " + bloodA + " 点生命。");
        System.out.println("B 剩余 " + bloodB + " 点生命。");
    }
}
