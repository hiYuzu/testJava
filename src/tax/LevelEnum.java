package tax;

/**
 * @author hiYuzu
 * @version V1.0
 * @date 2021/1/11 13:52
 */
public enum LevelEnum {
    LEVEL_1(0.03,0),
    LEVEL_2(0.1,210),
    LEVEL_3(0.2,1410),
    LEVEL_4(0.25,2660),
    LEVEL_5(0.3,4410),
    LEVEL_6(0.35,7160),
    LEVEL_7(0.45,15160);
    private final double percent;
    private final int deductionYear;
    private final int deductionMonth;
    LevelEnum(double percent, int deductionMonth) {
        this.percent = percent;
        this.deductionMonth = deductionMonth;
        this.deductionYear = deductionMonth * 12;
    }
    public double getPercent() {
        return percent;
    }
    public int getDeductionYear() {
        return deductionYear;
    }

    public int getDeductionMonth() {
        return deductionMonth;
    }
}