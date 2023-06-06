package agws;

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
 * <p>控烟年度工作填报</p>
 * <p>JDK版本：1.8</p>
 *
 * @author yuzu
 * @version v1.0
 * @since 2023/6/5 15:55
 */
public class TestTobaccoYear {
    private static final String SYS_PROTOCOL = "http";
    private static final String SYS_IP = "127.0.0.1";
    private static final String SYS_PORT = "80";
    private static final String SYS_METHOD = "/openApi/tobacco/tobaccoYear";
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
        String content = JSONUtil.toJsonStr(AwbYearTobControlEntity.getOne());
        // SM2加密
        byte[] publicKey = HexUtil.decodeHex(PUBLIC_KEY_STR);
        SM2 sm2 = SmUtil.sm2();
        sm2.setMode(SM2Engine.Mode.C1C2C3);
        sm2.setPublicKey(KeyUtil.generatePublicKey("SM2", publicKey));
        String result = sm2.encryptBase64(content, StandardCharsets.UTF_8, KeyType.PublicKey);
        System.out.println("密文：" + result);
        // 传参Map
        Map<String, Object> paramMap = new HashMap<>(3);
        // 机构编码，这里以山东省爱卫办为例
        paramMap.put("orgCode", "37000000000");
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
     * 控烟年度工作所有字段
     */
    public static class AwbYearTobControlEntity {
        /**
         * 行政区划编码（9位码，补0），必填
         */
        private String regionname;
        /**
         * 控烟工作专职人员总数
         */
        private Integer tobaccocontrolworkers;
        /**
         * 控烟工作兼职人员总数
         */
        private Integer controlparttimers;
        /**
         * 戒烟门诊数量
         */
        private Integer cessationclinics;
        /**
         * 戒烟门诊首诊干预量
         */
        private BigDecimal averagequitsmokinginterventions;
        /**
         * 学校总数量
         */
        private BigDecimal schoolsall;
        /**
         * 各级党政机关总数量
         */
        private BigDecimal numberoforgans;
        /**
         * 戒烟门诊随访干预量
         */
        private BigDecimal smokingcessationfollowup;
        /**
         * 医疗卫生单位总量
         */
        private BigDecimal medicalunitnum;
        /**
         * 全面无烟医疗卫生系统单位建成数量
         */
        private BigDecimal nosmokingunitstandardrate;
        /**
         * 无烟党政机关建成数量
         */
        private BigDecimal nosmokinggovernmentunits;
        /**
         * 全面无烟学校建成数量
         */
        private BigDecimal nosmokingschools;
        /**
         * 过去12个月参加国家级及省级控烟培训班的总人次数
         */
        private Integer tobaccocontroltrainingpeople;

        public String getRegionname() {
            return regionname;
        }

        public void setRegionname(String regionname) {
            this.regionname = regionname;
        }

        public Integer getTobaccocontrolworkers() {
            return tobaccocontrolworkers;
        }

        public void setTobaccocontrolworkers(Integer tobaccocontrolworkers) {
            this.tobaccocontrolworkers = tobaccocontrolworkers;
        }

        public Integer getControlparttimers() {
            return controlparttimers;
        }

        public void setControlparttimers(Integer controlparttimers) {
            this.controlparttimers = controlparttimers;
        }

        public Integer getCessationclinics() {
            return cessationclinics;
        }

        public void setCessationclinics(Integer cessationclinics) {
            this.cessationclinics = cessationclinics;
        }

        public BigDecimal getAveragequitsmokinginterventions() {
            return averagequitsmokinginterventions;
        }

        public void setAveragequitsmokinginterventions(BigDecimal averagequitsmokinginterventions) {
            this.averagequitsmokinginterventions = averagequitsmokinginterventions;
        }

        public BigDecimal getSchoolsall() {
            return schoolsall;
        }

        public void setSchoolsall(BigDecimal schoolsall) {
            this.schoolsall = schoolsall;
        }

        public BigDecimal getNumberoforgans() {
            return numberoforgans;
        }

        public void setNumberoforgans(BigDecimal numberoforgans) {
            this.numberoforgans = numberoforgans;
        }

        public BigDecimal getSmokingcessationfollowup() {
            return smokingcessationfollowup;
        }

        public void setSmokingcessationfollowup(BigDecimal smokingcessationfollowup) {
            this.smokingcessationfollowup = smokingcessationfollowup;
        }

        public BigDecimal getMedicalunitnum() {
            return medicalunitnum;
        }

        public void setMedicalunitnum(BigDecimal medicalunitnum) {
            this.medicalunitnum = medicalunitnum;
        }

        public BigDecimal getNosmokingunitstandardrate() {
            return nosmokingunitstandardrate;
        }

        public void setNosmokingunitstandardrate(BigDecimal nosmokingunitstandardrate) {
            this.nosmokingunitstandardrate = nosmokingunitstandardrate;
        }

        public BigDecimal getNosmokinggovernmentunits() {
            return nosmokinggovernmentunits;
        }

        public void setNosmokinggovernmentunits(BigDecimal nosmokinggovernmentunits) {
            this.nosmokinggovernmentunits = nosmokinggovernmentunits;
        }

        public BigDecimal getNosmokingschools() {
            return nosmokingschools;
        }

        public void setNosmokingschools(BigDecimal nosmokingschools) {
            this.nosmokingschools = nosmokingschools;
        }

        public Integer getTobaccocontroltrainingpeople() {
            return tobaccocontroltrainingpeople;
        }

        public void setTobaccocontroltrainingpeople(Integer tobaccocontroltrainingpeople) {
            this.tobaccocontroltrainingpeople = tobaccocontroltrainingpeople;
        }

        public AwbYearTobControlEntity(String regionname) {
            this.regionname = regionname;
        }

        public static AwbYearTobControlEntity getOne() {
            // 以山东省潍坊市为例
            return new AwbYearTobControlEntity("370700000");
        }
    }
}
