import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * 计算 π 值
 *
 * @author hiYuzu
 * @version V1.0
 * @date 2021/4/22 16:52
 */
public class CalculatePi {
    /**
     * 固定值，恒为 2
     */
    final BigDecimal i = new BigDecimal(2);
    /**
     * 开方计算的迭代次数（牛顿迭代法）以及结果的保留小数位数，数值越大，精度越高
     */
    final int scaleAndPrecision = 5000;
    /**
     * 2的k次幂即为多边形边数, 同时用 k 控制递归次数，数值越大，精度越高
     */
    final int k = 500;

    public static void main(String[] args) {
        try {
            CalculatePi calculatePi = new CalculatePi();
            long startTime = System.currentTimeMillis();
            // 开始计算
            BigDecimal calValue = calculatePi.calPi();
            long endCalTime = System.currentTimeMillis();
            System.out.println("计算耗时：" + (endCalTime - startTime));
            long startCompareTime = System.currentTimeMillis();
            // 开始比较
            calculatePi.countIndex(calValue);
            System.out.println("比较耗时：" + (System.currentTimeMillis() - startCompareTime));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 计算 π 值
     *
     * @return π
     */
    private BigDecimal calPi() {
        // n 边形
        BigDecimal n = i.pow(k);
        // 公式计算 2^k + sqrt(2 - sqrt(2 + sqrt(2 + ...)))
        return n.multiply(sqrt(i.subtract(sqrtCycle(k - 1))));
    }

    /**
     * 递归计算
     *
     * @param k 2的k次幂即为多边形边数, 这里用 k 控制递归次数
     * @return 递归结果
     */
    private BigDecimal sqrtCycle(int k) {
        k--;
        if (k > 0) {
            return sqrt(i.add(sqrtCycle(k)));
        } else {
            return sqrt(i);
        }
    }

    /**
     * 开方计算
     *
     * @param value 被开方的值
     * @return 开方结果
     */
    private BigDecimal sqrt(BigDecimal value) {
        BigDecimal num2 = BigDecimal.valueOf(2);
        MathContext mc = new MathContext(scaleAndPrecision, RoundingMode.HALF_UP);
        BigDecimal deviation = value;
        int count = 0;
        while (count < scaleAndPrecision) {
            deviation = (deviation.add(value.divide(deviation, mc))).divide(num2, mc);
            count++;
        }
        deviation = deviation.setScale(scaleAndPrecision, RoundingMode.HALF_UP);
        return deviation;
    }

    /**
     * 比较计算结果与实际 π 值的差异，实际 π 值数据来源：
     * <link>http://www.geom.uiuc.edu/~huberty/math5337/groupe/digits.html<link/>
     *
     * @param calValue 计算的 π 值
     */
    private void countIndex(BigDecimal calValue) {
        String calStr = calValue.toString();
        char[] calChars = calStr.toCharArray();
        String realStr = "3.141592653589793238462643383279502884197169399375105820974944592307816406286208998628034825342117067982148086513282306647093844609550582231725359408128481117450284102701938521105559644622948954930381964428810975665933446128475648233786783165271201909145648566923460348610454326648213393607260249141273724587006606315588174881520920962829254091715364367892590360011330530548820466521384146951941511609433057270365759591953092186117381932611793105118548074462379962749567351885752724891227938183011949129833673362440656643086021394946395224737190702179860943702770539217176293176752384674818467669405132000568127145263560827785771342757789609173637178721468440901224953430146549585371050792279689258923542019956112129021960864034418159813629774771309960518707211349999998372978049";
        char[] realChars = realStr.replace(" ", "").toCharArray();
        int i = 0;
        while (true) {
            if (calChars[i] != realChars[i]) {
                System.out.println("索引为" + i + "处不同(即精确到小数点后第" + (i - 2) + "位)：\n" + calChars[i - 1] + calChars[i] + calChars[i + 1] + " != " + realChars[i - 1] + realChars[i] + realChars[i + 1]);
                break;
            }
            i++;
        }
    }
}
