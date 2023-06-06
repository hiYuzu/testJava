package agws;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>卫生乡镇资料评估</p>
 * <p>JDK版本：1.8</p>
 *
 * @author sinosoft
 * @version V2.0
 * @date 2023/3/20 13:25
 */
public class TestSanCountryFile {
    private static final String SYS_PROTOCOL = "http";
    private static final String SYS_IP = "61.49.19.16";
    private static final String SYS_PORT = "80";
    private static final String SYS_METHOD = "/openApi/sanEva/countryFile";
    private static final String IIG_HEADER = "IIG-AUTH";
    private static final String IIG_AUTH = "jlf5ydoq-u7dh-olrp-n2mk-a8lrc8q3nfkw";

    /**
     * 测试入口
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        File file1 = FileUtil.file("D:\\1.pdf");
        File file2 = FileUtil.file("D:\\2.pdf");
        // 传参Map
        Map<String, Object> paramMap = new HashMap<>(7);
        // 机构编码，这里以山东省爱卫办为例
        paramMap.put("orgCode", "37000000000");
        // 地区编码，这里以山东省潍坊市潍城区北关街道为例
        paramMap.put("regionName", "370702004");
        // 资料类型
        // 1：卫生县创建；2：卫生县复审；3：其他
        paramMap.put("xzzlpglx", "3");
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
         * 建成区范围、地理位置及人口
         */
        GEOGRAPHICAL("geographical", "geographicalName"),
        /**
         * 经济和社会发展情况
         */
        DEVELOPMENT("development", "developmentName"),
        /**
         * 社区、村名单
         */
        COMMUNITIES("communities", "communitiesName"),
        /**
         * 辖区规划图和交通图
         */
        DISTRICT_PLAN("districtPlan", "districtPlanName"),
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

