package api.agws;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>卫生城市资料评估</p>
 * <p>JDK版本：1.8</p>
 *
 * @author sinosoft
 * @version V2.0
 * @date 2023/3/2 14:51
 */
public class TestSanCityFile {
    private static final String SYS_PROTOCOL = "http";
    private static final String SYS_IP = "127.0.0.1";
    private static final String SYS_PORT = "80";
    private static final String SYS_METHOD = "/openApi/sanEva/cityFile";
    private static final String IIG_HEADER = "IIG-AUTH";
    private static final String IIG_AUTH = "jlf5ydoq-u7dh-olrp-n2mk-a8lrc8q3nfkw";

    /**
     * 测试入口
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        File file1 = FileUtil.file("E:\\1.pdf");
        File file2 = FileUtil.file("E:\\2.pdf");
        // 传参Map
        Map<String, Object> paramMap = new HashMap<>(7);
        // 机构编码，这里以山东省爱卫办为例
        paramMap.put("orgCode", "37000000000");
        // 地区编码，这里以山东省潍坊市为例
        paramMap.put("regionName", "370700000");
        // 资料类型
        // 1：卫生城市创建；2：卫生城市复审；3：其他
        paramMap.put("cszlpglx", "1");
        paramMap.put(FileZlpgEnum.RECOMMENDED.getKey(), file1);
        paramMap.put(FileZlpgEnum.RECOMMENDED.getKeyName(), "爱卫会推荐报告.pdf");
        paramMap.put(FileZlpgEnum.OTHER_RELEVANT.getKey(), file2);
        paramMap.put(FileZlpgEnum.OTHER_RELEVANT.getKeyName(), "其他资料.pdf");

        final int timeout = 20000;
        String responseBody = HttpUtil.createPost(SYS_PROTOCOL + "://" + SYS_IP + ":" + SYS_PORT + SYS_METHOD)
                .header(IIG_HEADER, IIG_AUTH)
                .form(paramMap)
                .timeout(timeout)
                .execute()
                .body();
        System.out.println(responseBody);
    }

    public enum FileZlpgEnum {
        /**
         * 爱卫会推荐报告
         */
        RECOMMENDED("recommended", "recommendedName"),
        /**
         * 工作汇报
         */
        WORK_REPORT("workReport", "workReportName"),
        /**
         * 工作计划
         */
        WORK_PLAN("workPlan", "workPlanName"),
        /**
         * 实施方案
         */
        EMBODIMENT("embodiment", "embodimentName"),
        /**
         * 爱国卫生工作法规或规范性文件
         */
        REGULATIONS("regulations", "regulationsName"),
        /**
         * 爱卫办机构和人员组成
         */
        ORGANIZATION("organization", "organizationName"),
        /**
         * 建成区范围、地理位置及人口
         */
        GEOGRAPHICAL("geographical", "geographicalName"),
        /**
         * 经济和社会发展情况
         */
        DEVELOPMENT("development", "developmentName"),
        /**
         * 区、街道、乡镇、社区、村名单（城市）
         */
        COMMUNITIES("communities", "communitiesName"),
        /**
         * 城市规划图和交通图（城市）
         */
        TRAFFIC_PLAN("trafficPlan", "trafficPlanName"),
        /**
         * 其他相关资料
         */
        OTHER_RELEVANT("otherRelevant", "otherRelevantName");

        private String key;
        private String keyName;

        FileZlpgEnum(String key, String keyName) {
            this.key = key;
            this.keyName = keyName;
        }

        public static String getNameByKey(String key) {
            for (FileZlpgEnum zlpgEnum : FileZlpgEnum.values()) {
                if (zlpgEnum.getKey().equals(key)) {
                    return zlpgEnum.getKeyName();
                }
            }
            return null;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getKeyName() {
            return keyName;
        }

        public void setKeyName(String keyName) {
            this.keyName = keyName;
        }
    }
}

