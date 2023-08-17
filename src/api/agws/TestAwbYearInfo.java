package api.agws;

import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.KeyUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import org.bouncycastle.crypto.engines.SM2Engine;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>爱卫办年度工作情况填报</p>
 * <p>JDK版本：1.8</p>
 *
 * @author sinosoft
 * @version V1.0
 * @date 2023/3/7 15:47
 */
public class TestAwbYearInfo {
    private static final String SYS_PROTOCOL = "http";
    private static final String SYS_IP = "127.0.0.1";
    private static final String SYS_PORT = "80";
    private static final String SYS_METHOD = "/openApi/baseInfo/awbYearInfo";
    private static final String IIG_HEADER = "IIG-AUTH";
    private static final String IIG_AUTH = "jlf5ydoq-u7dh-olrp-n2mk-a8lrc8q3nfkw";
    private static final String PUBLIC_KEY_STR = "3059301306072a8648ce3d020106082a811ccf5501822d03420004640bef3e59e1431c27fd36a6fbfdbf8cdecaf5b16b16b747d57f3f312ad71ad678ab3487dcb928488b092e48631e9c7d19f0a75875df13d35bdbd6e33c681c06";

    /**
     * 测试入口
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        // JSON字符串格式
        String content = JSONUtil.toJsonStr(AwbYearBaseInfoEntity.getOne());
        // SM2加密
        byte [] publicKey = HexUtil.decodeHex(PUBLIC_KEY_STR);
        SM2 sm2 = SmUtil.sm2();
        sm2.setMode(SM2Engine.Mode.C1C2C3);
        sm2.setPublicKey(KeyUtil.generatePublicKey("SM2", publicKey));
        String result = sm2.encryptBase64(content, StandardCharsets.UTF_8, KeyType.PublicKey);
        System.out.println("密文：" + result);
        // 传参Map
        Map<String, Object> paramMap = new HashMap<>(3);
        // 机构编码，这里以辽宁省大连市为例
        paramMap.put("orgCode", "21020000000");
        // 加密后的数据
        paramMap.put("encStr", result);
        final int timeout = 20000;
        String responseBody = HttpUtil.createPost(SYS_PROTOCOL + "://" + SYS_IP + ":" + SYS_PORT + SYS_METHOD)
                .header(IIG_HEADER, IIG_AUTH)
                .form(paramMap)
                .timeout(timeout)
                .execute()
                .body();
        System.out.println(responseBody);
    }

    /**
     * 爱卫办年度工作情况所有字段
     */
    public static class AwbYearBaseInfoEntity {
        /**
         * 日常办公经费
         */
        private BigDecimal office_expenses;
        /**
         * 环境卫生整治
         */
        private BigDecimal environment_special_funds;
        /**
         * 卫生城镇创建
         */
        private BigDecimal health_special_funds;
        /**
         * 健康城镇健康细胞建设
         */
        private BigDecimal toilet_special_funds;
        /**
         * 病媒生物防制
         */
        private BigDecimal vector_special_funds;
        /**
         * 健康科普活动
         */
        private BigDecimal education_special_funds;
        /**
         * 控烟
         */
        private BigDecimal tobacco_special_funds;
        /**
         * 其他1
         */
        private BigDecimal other_special_funds;
        /**
         * 其他1名称
         */
        private String other_specialFunds_name;
        /**
         * 其他2
         */
        private BigDecimal other_special_fundstwo;
        /**
         * 其他2名称
         */
        private String other_specialFunds_names;
        /**
         * 召开爱卫会全体委员会议次数
         */
        private Integer conference_times;

        public BigDecimal getOffice_expenses() {
            return office_expenses;
        }

        public void setOffice_expenses(BigDecimal office_expenses) {
            this.office_expenses = office_expenses;
        }

        public BigDecimal getEnvironment_special_funds() {
            return environment_special_funds;
        }

        public void setEnvironment_special_funds(BigDecimal environment_special_funds) {
            this.environment_special_funds = environment_special_funds;
        }

        public BigDecimal getHealth_special_funds() {
            return health_special_funds;
        }

        public void setHealth_special_funds(BigDecimal health_special_funds) {
            this.health_special_funds = health_special_funds;
        }

        public BigDecimal getToilet_special_funds() {
            return toilet_special_funds;
        }

        public void setToilet_special_funds(BigDecimal toilet_special_funds) {
            this.toilet_special_funds = toilet_special_funds;
        }

        public BigDecimal getVector_special_funds() {
            return vector_special_funds;
        }

        public void setVector_special_funds(BigDecimal vector_special_funds) {
            this.vector_special_funds = vector_special_funds;
        }

        public BigDecimal getEducation_special_funds() {
            return education_special_funds;
        }

        public void setEducation_special_funds(BigDecimal education_special_funds) {
            this.education_special_funds = education_special_funds;
        }

        public BigDecimal getTobacco_special_funds() {
            return tobacco_special_funds;
        }

        public void setTobacco_special_funds(BigDecimal tobacco_special_funds) {
            this.tobacco_special_funds = tobacco_special_funds;
        }

        public BigDecimal getOther_special_funds() {
            return other_special_funds;
        }

        public void setOther_special_funds(BigDecimal other_special_funds) {
            this.other_special_funds = other_special_funds;
        }

        public String getOther_specialFunds_name() {
            return other_specialFunds_name;
        }

        public void setOther_specialFunds_name(String other_specialFunds_name) {
            this.other_specialFunds_name = other_specialFunds_name;
        }

        public BigDecimal getOther_special_fundstwo() {
            return other_special_fundstwo;
        }

        public void setOther_special_fundstwo(BigDecimal other_special_fundstwo) {
            this.other_special_fundstwo = other_special_fundstwo;
        }

        public String getOther_specialFunds_names() {
            return other_specialFunds_names;
        }

        public void setOther_specialFunds_names(String other_specialFunds_names) {
            this.other_specialFunds_names = other_specialFunds_names;
        }

        public Integer getConference_times() {
            return conference_times;
        }

        public void setConference_times(Integer conference_times) {
            this.conference_times = conference_times;
        }

        public AwbYearBaseInfoEntity(BigDecimal office_expenses, BigDecimal environment_special_funds, BigDecimal health_special_funds, Integer conference_times) {
            this.office_expenses = office_expenses;
            this.environment_special_funds = environment_special_funds;
            this.health_special_funds = health_special_funds;
            this.conference_times = conference_times;
        }

        public static AwbYearBaseInfoEntity getOne() {
            return new AwbYearBaseInfoEntity(new BigDecimal("20.00"), new BigDecimal("20.00"), new BigDecimal("20.00"), 10);
        }
    }
}

