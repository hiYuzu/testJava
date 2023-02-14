package sort;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;

/**
 * @author hiYuzu
 * @version V1.0
 * @date 2023/2/14 18:19
 */
public class CoupleArray {
    public static void main(String[] args) {
        String[] strings = { "2020", "2021", "", "2023", "2024" };
        BigDecimal[] bigDecimals = new BigDecimal[] { new BigDecimal("2.5"), new BigDecimal("2.6"), null, new BigDecimal("2.8"), new BigDecimal("2.9") };
        int sLength = strings.length;
        int[] ints = new int[sLength];
        for (int i = 0; i < sLength; i++) {
            try {
                ints[i] = Integer.parseInt(strings[i]);
            } catch (NumberFormatException nfe) {
                ints[i] = -1;
            }
        }
        final Object[][] res = new Object[sLength][2];
        for(int i = 0; i < res.length; i++) {
            res[i] = new Object[] {ints[i], bigDecimals[i]};
        }

        Arrays.sort(res, Comparator.comparingInt(a -> Integer.parseInt(String.valueOf(a[0]))));
        BigDecimal[] result = new BigDecimal[sLength];
        for (int i = 0, j = sLength - 1; i < sLength; i++, j--) {
            if ((int) res[j][0] != -1) {
                result[i] = (BigDecimal) res[j][1];
            } else {
                result[i] = null;
            }
        }
        for (BigDecimal b : result) {
            System.out.println(b);
        }
    }
}
