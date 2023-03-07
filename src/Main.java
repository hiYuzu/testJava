import cn.hutool.core.io.FileUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Base64;

/**
 * @author hiYuzu
 * @version V1.0
 * @date 2022/3/10 16:07
 */
public class Main {
    public static void main(String[] args) {
        BigDecimal[] items = new BigDecimal[] {new BigDecimal("12"), new BigDecimal("23"), new BigDecimal("12"), new BigDecimal("12"), new BigDecimal("12")};
        System.out.println(niceAvgItem(true, true, items));
    }

    public static boolean niceAvgItem(boolean containEq, boolean reverse, BigDecimal... items) {
        // 全部非空
        BigDecimal sum = BigDecimal.ZERO;
        BigDecimal first = items[0];
        for (BigDecimal item : items) {
            if (item == null) {
                return false;
            }
            sum = sum.add(item);
        }
        BigDecimal avg = sum.divide(BigDecimal.valueOf(items.length), RoundingMode.CEILING);
        int cr;
        if (reverse) {
            cr = -1;
            if (containEq) {
                cr = 0;
            }
            return first.compareTo(avg) <= cr;
        } else {
            cr = 1;
            if (containEq) {
                cr = 0;
            }
            return first.compareTo(avg) >= cr;
        }
    }
}