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
        String[] strings = { "2020", "2021", "2022", "2023", "2024" };
        BigDecimal[] bigDecimals = new BigDecimal[] { new BigDecimal("2.5"), new BigDecimal("2.6"), null, new BigDecimal("2.8"), new BigDecimal("2.9") };
        Integer[] integers = new Integer[] {null, 41, null, 55, 78};
        Integer[] result = new CoupleArray().sortItem(strings, integers);
        for (Integer i : result) {
            System.out.println(i);
            System.out.println((int) i >= 4);
        }
    }
    private BigDecimal[] sortItem(String[] years, BigDecimal[] items) {
        if (years.length == 0 || years.length != items.length) {
            return new BigDecimal[]{};
        }
        int sLength = years.length;
        int[] intYears = new int[sLength];
        for (int i = 0; i < sLength; i++) {
            try {
                intYears[i] = Integer.parseInt(years[i]);
            } catch (NumberFormatException nfe) {
                intYears[i] = -1;
            }
        }
        final Object[][] res = new Object[sLength][2];
        for (int i = 0; i < res.length; i++) {
            res[i] = new Object[]{intYears[i], items[i]};
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
        return result;
    }

    private Integer[] sortItem(String[] years, Integer[] items) {
        if (years.length == 0 || years.length != items.length) {
            return new Integer[]{};
        }
        int sLength = years.length;
        int[] intYears = new int[sLength];
        for (int i = 0; i < sLength; i++) {
            try {
                intYears[i] = Integer.parseInt(years[i]);
            } catch (NumberFormatException nfe) {
                intYears[i] = -1;
            }
        }
        final Integer[][] res = new Integer[sLength][2];
        for (int i = 0; i < res.length; i++) {
            res[i] = new Integer[]{intYears[i], items[i]};
        }

        Arrays.sort(res, Comparator.comparingInt(a -> a[0]));
        Integer[] result = new Integer[sLength];
        for (int i = 0, j = sLength - 1; i < sLength; i++, j--) {
            if ((int) res[j][0] != -1) {
                result[i] = res[j][1];
            } else {
                result[i] = null;
            }
        }
        return result;
    }
}
