import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yuzu
 * @version v1.0
 * @since 2025/1/22 13:39
 */
public class ModelUtil {
    private static final DecimalFormat adf = new DecimalFormat("0.00000");
    private static final DecimalFormat bdf = new DecimalFormat("0.0000");
    private static final DecimalFormat cdf = new DecimalFormat("0.000");
    private static final DecimalFormat dfb = new DecimalFormat("0.00");
    private static final DecimalFormat edf = new DecimalFormat("0.00E0");
    private static final DecimalFormat fdf = new DecimalFormat("0.0");
    private static final DecimalFormat gdf = new DecimalFormat("###");

    /**
     * 获取FFQ范围
     *
     * @param ffq 原始FFQ
     * @return FFQ范围
     */
    public static List<String> getFfqList(String ffq) {
        if (ffq == null) {
            return null;
        }
        List<String> ffqList = new ArrayList<>();
        if (ffq.contains("-")) {
            // 拆分字符串
            // 提取前缀字母
            String prefix = ffq.split("-")[0].replaceAll("\\d", "");
            // 提取起始数字
            int start = Integer.parseInt(ffq.split("-")[0].replaceAll("\\D", ""));
            // 提取结束数字
            int end = Integer.parseInt(ffq.split("-")[1]);
            // 根据范围生成列表
            for (int i = start; i <= end; i++) {
                ffqList.add(prefix + i);
            }
        } else {
            ffqList.add(ffq);
        }
        return ffqList;
    }

    /**
     * 计算EDI（Estimate Daily Intake，估计每日摄入量）
     *
     * @param n  当前计算的FFQ的类别数量
     * @param ci 不同食品中化学物的含量
     * @param mi 不同食品每日消费量
     * @param bw 体重
     * @return EDI
     */
    public static String calculateEdi(int n, double ci, double mi, double bw) {
        return format(((ci * mi) * n) / bw);
    }

    /**
     * 计算平均值
     *
     * @param list 原始数据集
     * @return 平均值
     */
    public static String getAvg(List<Double> list) {
        if (list == null || list.isEmpty()) {
            return "-";
        }
        return format(list.stream().mapToDouble(Double::doubleValue).average().orElse(0));
    }

    /**
     * 计算P50值
     *
     * @param list 原始数据集
     * @return P50值
     */
    public static String getP50(List<Double> list) {
        return format(getPValue(0.5D, list));
    }
    /**
     * 计算P90值
     *
     * @param list 原始数据集
     * @return P90值
     */
    public static String getP90(List<Double> list) {
        return format(getPValue(0.9D, list));
    }
    /**
     * 计算P95值
     *
     * @param list 原始数据集
     * @return P95值
     */
    public static String getP95(List<Double> list) {
        return format(getPValue(0.95D, list));
    }
    /**
     * 计算最大值
     *
     * @param list 原始数据集
     * @return 最大值
     */
    public static String getMax(List<Double> list) {
        if (list == null || list.isEmpty()) {
            return "-";
        }
        return format(list.stream().max(Double::compare).get());
    }

    private static String getPValue(double pz, List<Double> list) {
        if (list.size() == 1) {
            if (Double.parseDouble(list.get(0).toString()) < 0) {
                return "－";
            }
            return list.get(0).toString();
        }
        int n = list.size();
        Double d;
        double px = pz * (list.size() - 1);
        int i1 = (int) java.lang.Math.floor(px);
        double g = px - i1;
        if (g == 0) {
            d = list.get(i1);
        } else {
            d = (1 - g) * list.get(i1) + g * list.get(i1 + 1);
        }
        if (d < 0)
            return "－";
        else
            return String.valueOf(d);
    }

    private static String format(String d) {
        if (d == null || d.equals("-") || d.equals("－") || d.equals("/")) {
            return d;
        }
        return format(Double.parseDouble(d));
    }

    private static String format(double d) {
        if (d < 0) {
            return "-";
        }
        if (d > 0 && d < 0.001) {
            return edf.format(d);
        }
        if (d >= 0.001 && d < 0.01) {
            return adf.format(d);
        }
        if (d >= 0.01 && d < 0.1) {
            return bdf.format(d);
        }
        if (d >= 0.1 && d < 1) {
            return cdf.format(d);
        }
        if (d >= 1 && d < 10) {
            return dfb.format(d);
        }
        if (d >= 10 && d < 100) {
            return fdf.format(d);
        }
        if (d >= 100 && d < 1000) {
            return gdf.format(d);
        }
        if (d >= 1000) {
            return edf.format(d);
        }
        return String.valueOf(d);
    }
}
