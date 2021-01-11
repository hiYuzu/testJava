package tax;

/**
 * 用于计算年终奖判断选择：
 * 1. 单独计税
 * 2. 并入全年
 *
 * 标准详见 tax.md
 * @author hiYuzu
 * @version V1.0
 * @date 2021/1/11 11:56
 */
public class TaxCompute {
    private final double salary;
    private final double reward;
    private final double total;
    private final double special;

    private double resultComplex;
    private double resultSingle;
    private double resultMerge;

    public TaxCompute(double salary, double reward, double special) {
        this.salary = salary;
        this.reward = reward;
        this.total = salary + reward;
        this.special = special + 60000;
    }

    private LevelEnum calculatePercent(double d) {
        if (d <= 36000) {
            return LevelEnum.LEVEL_1;
        } else if (d <= 144000) {
            return LevelEnum.LEVEL_2;
        } else if (d <= 300000) {
            return LevelEnum.LEVEL_3;
        }
        else if (d <= 420000) {
            return LevelEnum.LEVEL_4;
        }
        else if (d <= 660000) {
            return LevelEnum.LEVEL_5;
        }
        else if (d <= 960000) {
            return LevelEnum.LEVEL_6;
        } else {
            return LevelEnum.LEVEL_7;
        }
    }

    private double complexTax() {
        double temp = salary - special;
        LevelEnum level = calculatePercent(temp);
        return temp * level.getPercent() - level.getDeductionYear();
    }

    private void singleCompute() {
        LevelEnum level = calculatePercent(reward);
        double complexTax = complexTax();
        resultSingle = complexTax + reward * level.getPercent() - level.getDeductionMonth();
        System.out.println("单独计算全年缴税：" + resultSingle);
    }

    private void mergeCompute() {
        double temp = total - special;
        LevelEnum level = calculatePercent(temp);
        resultMerge = temp * level.getPercent() - level.getDeductionYear();
        System.out.println("并入计算全年缴税：" + resultMerge);
    }

    public void calculate() {
        complexTax();
        singleCompute();
        mergeCompute();
        System.out.println("差值：" + Math.abs(resultMerge - resultSingle));
    }
}
