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
 * <p>卫生县评估填报</p>
 * <p>JDK版本：1.8</p>
 *
 * @author hiYuzu
 * @version V1.0
 * @date 2023/3/20 13:51
 */
public class TestSanCountyInfo {
    private static final String SYS_PROTOCOL = "http";
    private static final String SYS_IP = "127.0.0.1";
    private static final String SYS_PORT = "80";
    private static final String SYS_METHOD = "/openApi/sanEva/countyInfo";
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
        String content = JSONUtil.toJsonStr(AgwsSanitationCountyEvaluation.getOne());
        // SM2加密
        byte[] publicKey = HexUtil.decodeHex(PUBLIC_KEY_STR);
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
     * 卫生县评估所有字段
     */
    public static class AgwsSanitationCountyEvaluation {
        /**
         * 行政区划编码（9位码，补0），必填
         */
        private String regionname;
        /**
         * 国家卫生乡镇数量
         */
        private BigDecimal countycount;
        /**
         * 群众对卫生状况满意率
         */
        private BigDecimal urbanhealthsatisfactionrate;
        /**
         * 居民健康素养水平-年份1（yyyy，如2023，下同）
         */
        private String year1;
        /**
         * 居民健康素养水平-年份1对应的值
         */
        private BigDecimal healthqualityrate;
        /**
         * 居民健康素养水平-年份2
         */
        private String year2;
        /**
         * 居民健康素养水平-年份2对应的值
         */
        private BigDecimal healthqualityrate1;
        /**
         * 居民健康素养水平-年份3
         */
        private String year3;
        /**
         * 居民健康素养水平-年份3对应的值
         */
        private BigDecimal healthqualityrate2;
        /**
         * 建有全民健身场地设施的社区比例
         */
        private BigDecimal facilitycoveragerate;
        /**
         * 经常参加体育锻炼人数的比例
         */
        private BigDecimal exercisepeoplerate;
        /**
         * 人均体育场地面积
         */
        private BigDecimal persportsarea;
        /**
         * 每千人口社会体育指导员数
         */
        private BigDecimal athleticdirectorrate;
        /**
         * 15岁以上人群吸烟率
         */
        private BigDecimal smokingoverfifteenrate;
        /**
         * 无烟党政机关建成比例
         */
        private BigDecimal nosmokingorgrate;
        /**
         * 无烟医疗卫生机构建成比例
         */
        private BigDecimal nosmokingmedicalrate;
        /**
         * 无烟学校建成比例
         */
        private BigDecimal nosmokingschoolrate;
        /**
         * 全面控烟法律法规规定
         * 值【1：有；2：无】
         */
        private String tobaccoadsituation;
        /**
         * 道路装灯率
         */
        private BigDecimal streetlamprate;
        /**
         * 主次干道每日保洁时间
         */
        private BigDecimal mainstreetcleaningtime;
        /**
         * 街巷路面每日保洁时间
         */
        private BigDecimal generalstreetcleaningtime;
        /**
         * 建成区绿化覆盖率
         */
        private BigDecimal greenlandcoveragerate;
        /**
         * 人均公园绿地面积
         */
        private BigDecimal pergreenlandarea;
        /**
         * 城市生活垃圾回收利用率
         */
        private BigDecimal garbagerecoveryrate;
        /**
         * 城市生活垃圾无害化处理率
         */
        private BigDecimal domesticgarbageharmlessdisposal;
        /**
         * 城市生活污水集中收集率
         */
        private BigDecimal domesticsewagedisposal;
        /**
         * 环境空气质量指数（AQI）不超过100的天数-年份1
         */
        private String year7;
        /**
         * 环境空气质量指数（AQI）不超过100的天数-年份1对应的值
         */
        private Integer aqibelow100days;
        /**
         * 环境空气质量指数（AQI）不超过100的天数-年份2
         */
        private String year8;
        /**
         * 环境空气质量指数（AQI）不超过100的天数-年份2对应的值
         */
        private Integer aqibelow100days1;
        /**
         * 环境空气质量指数（AQI）不超过100的天数-年份3
         */
        private String year9;
        /**
         * 环境空气质量指数（AQI）不超过100的天数-年份3对应的值
         */
        private Integer aqibelow100days2;
        /**
         * 环境空气主要污染物年均值-二氧化硫(SO2)
         */
        private BigDecimal hypertensionbloodpressurecontrolrate;
        /**
         * 环境空气主要污染物年均值-二氧化氮(NO2)
         */
        private Integer healthstreets;
        /**
         * 环境空气主要污染物年均值-颗粒物(PM10)
         */
        private Integer healthcommunities;
        /**
         * 环境空气主要污染物年均值-颗粒物(PM2.5)
         */
        private Integer healthunits;
        /**
         * 区域环境噪声控制平均值
         */
        private BigDecimal areanoiseavg;
        /**
         * 声功能区环境质量夜间达标率
         */
        private BigDecimal peripheralwaterstandardrate;
        /**
         * 集中式饮用水水源地水质达标率
         */
        private BigDecimal potablewaterrate;
        /**
         * 医疗废物无害化处置率
         */
        private BigDecimal medicalwastedisposal;
        /**
         * 学校校医或专(兼)职保健教师配备比率
         */
        private BigDecimal schooldoctorrate;
        /**
         * 中小学体育与健康课程开课率
         */
        private BigDecimal healtheducationrate;
        /**
         * 中小学生每天校内体育活动时间
         */
        private BigDecimal healtheducation;
        /**
         * 学校眼保健操普及率
         */
        private BigDecimal publicgoodsgradedrate;
        /**
         * 中小学生近视率-年份1
         */
        private String year13;
        /**
         * 中小学生近视率-年份1对应的值
         */
        private BigDecimal stumyopiarate;
        /**
         * 中小学生近视率-年份2
         */
        private String year14;
        /**
         * 中小学生近视率-年份2对应的值
         */
        private BigDecimal stumyopiarate1;
        /**
         * 中小学生近视率-年份3
         */
        private String year15;
        /**
         * 中小学生近视率-年份3对应的值
         */
        private BigDecimal stumyopiarate2;
        /**
         * 中小学生肥胖率-年份1
         */
        private String year32;
        /**
         * 中小学生肥胖率-年份1对应的值
         */
        private BigDecimal stuobesityrate;
        /**
         * 中小学生肥胖率-年份2
         */
        private String year33;
        /**
         * 中小学生肥胖率-年份2对应的值
         */
        private BigDecimal stuobesityrate1;
        /**
         * 中小学生肥胖率-年份3
         */
        private String year34;
        /**
         * 中小学生肥胖率-年份3对应的值
         */
        private BigDecimal stuobesityrate2;
        /**
         * 存在职业病目录所列职业病危害因素的企业职业病危害项目申报率
         */
        private BigDecimal workdiseaserate;
        /**
         * 食品生产经营风险分级管理率
         */
        private BigDecimal foodsaftygradedrate;
        /**
         * 个人卫生支出占卫生总费用的比重-年份1
         */
        private String year35;
        /**
         * 个人卫生支出占卫生总费用的比重-年份1对应的值
         */
        private BigDecimal perhealthspendrate;
        /**
         * 个人卫生支出占卫生总费用的比重-年份2
         */
        private String year36;
        /**
         * 个人卫生支出占卫生总费用的比重-年份2对应的值
         */
        private BigDecimal perhealthspendrate1;
        /**
         * 个人卫生支出占卫生总费用的比重-年份3
         */
        private String year37;
        /**
         * 个人卫生支出占卫生总费用的比重-年份3对应的值
         */
        private BigDecimal perhealthspendrate2;
        /**
         * 甲、乙类法定传染病报告发病率（5年平均水平）
         */
        private BigDecimal infectillavgrateAvg;
        /**
         * 甲、乙类法定传染病报告发病率-（当前年份-1）年对应的值（如今年2023年，此值为2022年数据值）
         */
        private BigDecimal infectillavgrate;
        /**
         * 甲、乙类法定传染病报告发病率-（当前年份-2）年对应的值
         */
        private BigDecimal infectillavgrate1;
        /**
         * 甲、乙类法定传染病报告发病率-（当前年份-3）年对应的值
         */
        private BigDecimal infectillavgrate2;
        /**
         * 甲、乙类法定传染病报告发病率-（当前年份-4）年对应的值
         */
        private BigDecimal infectillavgrate3;
        /**
         * 甲、乙类法定传染病报告发病率-（当前年份-5）年对应的值
         */
        private BigDecimal infectillavgrate4;
        /**
         * 婴儿死亡率-年份1
         */
        private String year21;
        /**
         * 婴儿死亡率-年份1对应的值
         */
        private BigDecimal infantmortalityrate;
        /**
         * 婴儿死亡率-年份2
         */
        private String year22;
        /**
         * 婴儿死亡率-年份2对应的值
         */
        private BigDecimal infantmortalityrate1;
        /**
         * 婴儿死亡率-年份3
         */
        private String year23;
        /**
         * 婴儿死亡率-年份3对应的值
         */
        private BigDecimal infantmortalityrate2;
        /**
         * 5岁以下儿童死亡率-年份1
         */
        private String year38;
        /**
         * 5岁以下儿童死亡率-年份1对应的值
         */
        private BigDecimal underfivemortalityrate;
        /**
         * 5岁以下儿童死亡率-年份2
         */
        private String year39;
        /**
         * 5岁以下儿童死亡率-年份2对应的值
         */
        private BigDecimal underfivemortalityrate1;
        /**
         * 5岁以下儿童死亡率-年份2
         */
        private String year40;
        /**
         * 5岁以下儿童死亡率-年份3对应的值
         */
        private BigDecimal underfivemortalityrate2;
        /**
         * 孕产妇死亡率-年份1
         */
        private String year24;
        /**
         * 孕产妇死亡率-年份1对应的值
         */
        private BigDecimal maternalmortalityrate;
        /**
         * 孕产妇死亡率-年份2
         */
        private String year25;
        /**
         * 孕产妇死亡率-年份2对应的值
         */
        private BigDecimal maternalmortalityrate1;
        /**
         * 孕产妇死亡率-年份3
         */
        private String year26;
        /**
         * 孕产妇死亡率-年份3对应的值
         */
        private BigDecimal maternalmortalityrate2;
        /**
         * 人均预期寿命-年份1
         */
        private String year29;
        /**
         * 人均预期寿命-年份1对应的值
         */
        private BigDecimal perexpectlife;
        /**
         * 人均预期寿命-年份2
         */
        private String year30;
        /**
         * 人均预期寿命-年份2对应的值
         */
        private BigDecimal perexpectlife1;
        /**
         * 人均预期寿命-年份3
         */
        private String year31;
        /**
         * 人均预期寿命-年份3对应的值
         */
        private BigDecimal perexpectlife2;
        /**
         * 以街道(乡、镇)为单位适龄儿童免疫规划疫苗接种率
         */
        private BigDecimal vaccinationcoveragerate;
        /**
         * 居住满3个月以上的适龄儿童建卡、建证率
         */
        private BigDecimal agedchildrencardrate;
        /**
         * 辖区内3岁以下儿童系统管理率
         */
        private BigDecimal populationhealthmanagementrate;
        /**
         * 0-6岁儿童眼保健和视力检查率
         */
        private BigDecimal healthexaminationcoveragerate;
        /**
         * 重大慢性病过早死亡率-年份1
         */
        private String year41;
        /**
         * 重大慢性病过早死亡率-年份1对应的值
         */
        private BigDecimal diabeticbloodsugarcontrolrate;
        /**
         * 重大慢性病过早死亡率-年份2
         */
        private String year42;
        /**
         * 重大慢性病过早死亡率-年份2对应的值
         */
        private BigDecimal diabeticbloodsugarcontrolrate1;
        /**
         * 重大慢性病过早死亡率-年份3
         */
        private String year43;
        /**
         * 重大慢性病过早死亡率-年份3对应的值
         */
        private BigDecimal diabeticbloodsugarcontrolrate2;
        /**
         * 严重精神障碍患者规范管理率
         */
        private BigDecimal neuropathymanagementrate;
        /**
         * 每千常住人口医疗卫生机构床位数
         */
        private BigDecimal diseaseequipmentstandardrate;
        /**
         * 所在地区域卫生规划要求的医疗卫生机构床位数
         */
        private BigDecimal blooddonationrate;
        /**
         * 每千常住人口执业（助理）医师数
         */
        private BigDecimal secondpolyclinic;
        /**
         * 所在地区域卫生规划要求的执业（助理）医师数
         */
        private BigDecimal infectiousdiseasedepartments;
        /**
         * 每千常住人口注册护士数
         */
        private BigDecimal healthconstructionstandardrate;
        /**
         * 所在地区域卫生规划要求的注册护士数
         */
        private BigDecimal insectcontrolinstallationqualificationrate;
        /**
         * 每千常住人口公共卫生人员数
         */
        private BigDecimal publictoilets;
        /**
         * 所在地区域卫生规划要求的公共卫生人员数
         */
        private BigDecimal secondpublictoilets;
        /**
         * 每千常住人口药师（药士）数
         */
        private BigDecimal agriculturalproductsmarketsrate;
        /**
         * 所在地区域卫生规划要求的药师（药士）数
         */
        private BigDecimal apibelow100days;
        /**
         * 每万常住人口全科医生数
         */
        private BigDecimal daytimenoiseaverage;
        /**
         * 所在地区域卫生规划要求的全科医生数
         */
        private BigDecimal urbanwaterworks;
        /**
         * 建成区鼠密度
         * 值：【01：A级；02：B级；03：C级；04：D级】
         */
        private String dicratpopulcationlevel;
        /**
         * 建成区蚊密度
         * 值：【01：A级；02：B级；03：C级；04：D级】
         */
        private String dicmosquitopopulcationlevel;
        /**
         * 建成区蝇密度
         * 值：【01：A级；02：B级；03：C级；04：D级】
         */
        private String dicflypopulcationlevel;
        /**
         * 建成区蟑螂密度
         * 值：【01：A级；02：B级；03：C级；04：D级】
         */
        private String diccockroachpopulcationlevel;
        /**
         * 重点行业和单位防蝇设施合格率
         */
        private BigDecimal tapwaterstandardrate;
        /**
         * 重点行业和单位防鼠设施合格率
         */
        private BigDecimal stumyopia;

        public String getRegionname() {
            return regionname;
        }

        public void setRegionname(String regionname) {
            this.regionname = regionname;
        }

        public BigDecimal getCountycount() {
            return countycount;
        }

        public void setCountycount(BigDecimal countycount) {
            this.countycount = countycount;
        }

        public BigDecimal getUrbanhealthsatisfactionrate() {
            return urbanhealthsatisfactionrate;
        }

        public void setUrbanhealthsatisfactionrate(BigDecimal urbanhealthsatisfactionrate) {
            this.urbanhealthsatisfactionrate = urbanhealthsatisfactionrate;
        }

        public String getYear1() {
            return year1;
        }

        public void setYear1(String year1) {
            this.year1 = year1;
        }

        public BigDecimal getHealthqualityrate() {
            return healthqualityrate;
        }

        public void setHealthqualityrate(BigDecimal healthqualityrate) {
            this.healthqualityrate = healthqualityrate;
        }

        public String getYear2() {
            return year2;
        }

        public void setYear2(String year2) {
            this.year2 = year2;
        }

        public BigDecimal getHealthqualityrate1() {
            return healthqualityrate1;
        }

        public void setHealthqualityrate1(BigDecimal healthqualityrate1) {
            this.healthqualityrate1 = healthqualityrate1;
        }

        public String getYear3() {
            return year3;
        }

        public void setYear3(String year3) {
            this.year3 = year3;
        }

        public BigDecimal getHealthqualityrate2() {
            return healthqualityrate2;
        }

        public void setHealthqualityrate2(BigDecimal healthqualityrate2) {
            this.healthqualityrate2 = healthqualityrate2;
        }

        public BigDecimal getFacilitycoveragerate() {
            return facilitycoveragerate;
        }

        public void setFacilitycoveragerate(BigDecimal facilitycoveragerate) {
            this.facilitycoveragerate = facilitycoveragerate;
        }

        public BigDecimal getExercisepeoplerate() {
            return exercisepeoplerate;
        }

        public void setExercisepeoplerate(BigDecimal exercisepeoplerate) {
            this.exercisepeoplerate = exercisepeoplerate;
        }

        public BigDecimal getPersportsarea() {
            return persportsarea;
        }

        public void setPersportsarea(BigDecimal persportsarea) {
            this.persportsarea = persportsarea;
        }

        public BigDecimal getAthleticdirectorrate() {
            return athleticdirectorrate;
        }

        public void setAthleticdirectorrate(BigDecimal athleticdirectorrate) {
            this.athleticdirectorrate = athleticdirectorrate;
        }

        public BigDecimal getSmokingoverfifteenrate() {
            return smokingoverfifteenrate;
        }

        public void setSmokingoverfifteenrate(BigDecimal smokingoverfifteenrate) {
            this.smokingoverfifteenrate = smokingoverfifteenrate;
        }

        public BigDecimal getNosmokingorgrate() {
            return nosmokingorgrate;
        }

        public void setNosmokingorgrate(BigDecimal nosmokingorgrate) {
            this.nosmokingorgrate = nosmokingorgrate;
        }

        public BigDecimal getNosmokingmedicalrate() {
            return nosmokingmedicalrate;
        }

        public void setNosmokingmedicalrate(BigDecimal nosmokingmedicalrate) {
            this.nosmokingmedicalrate = nosmokingmedicalrate;
        }

        public BigDecimal getNosmokingschoolrate() {
            return nosmokingschoolrate;
        }

        public void setNosmokingschoolrate(BigDecimal nosmokingschoolrate) {
            this.nosmokingschoolrate = nosmokingschoolrate;
        }

        public String getTobaccoadsituation() {
            return tobaccoadsituation;
        }

        public void setTobaccoadsituation(String tobaccoadsituation) {
            this.tobaccoadsituation = tobaccoadsituation;
        }

        public BigDecimal getStreetlamprate() {
            return streetlamprate;
        }

        public void setStreetlamprate(BigDecimal streetlamprate) {
            this.streetlamprate = streetlamprate;
        }

        public BigDecimal getMainstreetcleaningtime() {
            return mainstreetcleaningtime;
        }

        public void setMainstreetcleaningtime(BigDecimal mainstreetcleaningtime) {
            this.mainstreetcleaningtime = mainstreetcleaningtime;
        }

        public BigDecimal getGeneralstreetcleaningtime() {
            return generalstreetcleaningtime;
        }

        public void setGeneralstreetcleaningtime(BigDecimal generalstreetcleaningtime) {
            this.generalstreetcleaningtime = generalstreetcleaningtime;
        }

        public BigDecimal getGreenlandcoveragerate() {
            return greenlandcoveragerate;
        }

        public void setGreenlandcoveragerate(BigDecimal greenlandcoveragerate) {
            this.greenlandcoveragerate = greenlandcoveragerate;
        }

        public BigDecimal getPergreenlandarea() {
            return pergreenlandarea;
        }

        public void setPergreenlandarea(BigDecimal pergreenlandarea) {
            this.pergreenlandarea = pergreenlandarea;
        }

        public BigDecimal getGarbagerecoveryrate() {
            return garbagerecoveryrate;
        }

        public void setGarbagerecoveryrate(BigDecimal garbagerecoveryrate) {
            this.garbagerecoveryrate = garbagerecoveryrate;
        }

        public BigDecimal getDomesticgarbageharmlessdisposal() {
            return domesticgarbageharmlessdisposal;
        }

        public void setDomesticgarbageharmlessdisposal(BigDecimal domesticgarbageharmlessdisposal) {
            this.domesticgarbageharmlessdisposal = domesticgarbageharmlessdisposal;
        }

        public BigDecimal getDomesticsewagedisposal() {
            return domesticsewagedisposal;
        }

        public void setDomesticsewagedisposal(BigDecimal domesticsewagedisposal) {
            this.domesticsewagedisposal = domesticsewagedisposal;
        }

        public String getYear7() {
            return year7;
        }

        public void setYear7(String year7) {
            this.year7 = year7;
        }

        public Integer getAqibelow100days() {
            return aqibelow100days;
        }

        public void setAqibelow100days(Integer aqibelow100days) {
            this.aqibelow100days = aqibelow100days;
        }

        public String getYear8() {
            return year8;
        }

        public void setYear8(String year8) {
            this.year8 = year8;
        }

        public Integer getAqibelow100days1() {
            return aqibelow100days1;
        }

        public void setAqibelow100days1(Integer aqibelow100days1) {
            this.aqibelow100days1 = aqibelow100days1;
        }

        public String getYear9() {
            return year9;
        }

        public void setYear9(String year9) {
            this.year9 = year9;
        }

        public Integer getAqibelow100days2() {
            return aqibelow100days2;
        }

        public void setAqibelow100days2(Integer aqibelow100days2) {
            this.aqibelow100days2 = aqibelow100days2;
        }

        public BigDecimal getHypertensionbloodpressurecontrolrate() {
            return hypertensionbloodpressurecontrolrate;
        }

        public void setHypertensionbloodpressurecontrolrate(BigDecimal hypertensionbloodpressurecontrolrate) {
            this.hypertensionbloodpressurecontrolrate = hypertensionbloodpressurecontrolrate;
        }

        public Integer getHealthstreets() {
            return healthstreets;
        }

        public void setHealthstreets(Integer healthstreets) {
            this.healthstreets = healthstreets;
        }

        public Integer getHealthcommunities() {
            return healthcommunities;
        }

        public void setHealthcommunities(Integer healthcommunities) {
            this.healthcommunities = healthcommunities;
        }

        public Integer getHealthunits() {
            return healthunits;
        }

        public void setHealthunits(Integer healthunits) {
            this.healthunits = healthunits;
        }

        public BigDecimal getAreanoiseavg() {
            return areanoiseavg;
        }

        public void setAreanoiseavg(BigDecimal areanoiseavg) {
            this.areanoiseavg = areanoiseavg;
        }

        public BigDecimal getPeripheralwaterstandardrate() {
            return peripheralwaterstandardrate;
        }

        public void setPeripheralwaterstandardrate(BigDecimal peripheralwaterstandardrate) {
            this.peripheralwaterstandardrate = peripheralwaterstandardrate;
        }

        public BigDecimal getPotablewaterrate() {
            return potablewaterrate;
        }

        public void setPotablewaterrate(BigDecimal potablewaterrate) {
            this.potablewaterrate = potablewaterrate;
        }

        public BigDecimal getMedicalwastedisposal() {
            return medicalwastedisposal;
        }

        public void setMedicalwastedisposal(BigDecimal medicalwastedisposal) {
            this.medicalwastedisposal = medicalwastedisposal;
        }

        public BigDecimal getSchooldoctorrate() {
            return schooldoctorrate;
        }

        public void setSchooldoctorrate(BigDecimal schooldoctorrate) {
            this.schooldoctorrate = schooldoctorrate;
        }

        public BigDecimal getHealtheducationrate() {
            return healtheducationrate;
        }

        public void setHealtheducationrate(BigDecimal healtheducationrate) {
            this.healtheducationrate = healtheducationrate;
        }

        public BigDecimal getHealtheducation() {
            return healtheducation;
        }

        public void setHealtheducation(BigDecimal healtheducation) {
            this.healtheducation = healtheducation;
        }

        public BigDecimal getPublicgoodsgradedrate() {
            return publicgoodsgradedrate;
        }

        public void setPublicgoodsgradedrate(BigDecimal publicgoodsgradedrate) {
            this.publicgoodsgradedrate = publicgoodsgradedrate;
        }

        public String getYear13() {
            return year13;
        }

        public void setYear13(String year13) {
            this.year13 = year13;
        }

        public BigDecimal getStumyopiarate() {
            return stumyopiarate;
        }

        public void setStumyopiarate(BigDecimal stumyopiarate) {
            this.stumyopiarate = stumyopiarate;
        }

        public String getYear14() {
            return year14;
        }

        public void setYear14(String year14) {
            this.year14 = year14;
        }

        public BigDecimal getStumyopiarate1() {
            return stumyopiarate1;
        }

        public void setStumyopiarate1(BigDecimal stumyopiarate1) {
            this.stumyopiarate1 = stumyopiarate1;
        }

        public String getYear15() {
            return year15;
        }

        public void setYear15(String year15) {
            this.year15 = year15;
        }

        public BigDecimal getStumyopiarate2() {
            return stumyopiarate2;
        }

        public void setStumyopiarate2(BigDecimal stumyopiarate2) {
            this.stumyopiarate2 = stumyopiarate2;
        }

        public String getYear32() {
            return year32;
        }

        public void setYear32(String year32) {
            this.year32 = year32;
        }

        public BigDecimal getStuobesityrate() {
            return stuobesityrate;
        }

        public void setStuobesityrate(BigDecimal stuobesityrate) {
            this.stuobesityrate = stuobesityrate;
        }

        public String getYear33() {
            return year33;
        }

        public void setYear33(String year33) {
            this.year33 = year33;
        }

        public BigDecimal getStuobesityrate1() {
            return stuobesityrate1;
        }

        public void setStuobesityrate1(BigDecimal stuobesityrate1) {
            this.stuobesityrate1 = stuobesityrate1;
        }

        public String getYear34() {
            return year34;
        }

        public void setYear34(String year34) {
            this.year34 = year34;
        }

        public BigDecimal getStuobesityrate2() {
            return stuobesityrate2;
        }

        public void setStuobesityrate2(BigDecimal stuobesityrate2) {
            this.stuobesityrate2 = stuobesityrate2;
        }

        public BigDecimal getWorkdiseaserate() {
            return workdiseaserate;
        }

        public void setWorkdiseaserate(BigDecimal workdiseaserate) {
            this.workdiseaserate = workdiseaserate;
        }

        public BigDecimal getFoodsaftygradedrate() {
            return foodsaftygradedrate;
        }

        public void setFoodsaftygradedrate(BigDecimal foodsaftygradedrate) {
            this.foodsaftygradedrate = foodsaftygradedrate;
        }

        public String getYear35() {
            return year35;
        }

        public void setYear35(String year35) {
            this.year35 = year35;
        }

        public BigDecimal getPerhealthspendrate() {
            return perhealthspendrate;
        }

        public void setPerhealthspendrate(BigDecimal perhealthspendrate) {
            this.perhealthspendrate = perhealthspendrate;
        }

        public String getYear36() {
            return year36;
        }

        public void setYear36(String year36) {
            this.year36 = year36;
        }

        public BigDecimal getPerhealthspendrate1() {
            return perhealthspendrate1;
        }

        public void setPerhealthspendrate1(BigDecimal perhealthspendrate1) {
            this.perhealthspendrate1 = perhealthspendrate1;
        }

        public String getYear37() {
            return year37;
        }

        public void setYear37(String year37) {
            this.year37 = year37;
        }

        public BigDecimal getPerhealthspendrate2() {
            return perhealthspendrate2;
        }

        public void setPerhealthspendrate2(BigDecimal perhealthspendrate2) {
            this.perhealthspendrate2 = perhealthspendrate2;
        }

        public BigDecimal getInfectillavgrateAvg() {
            return infectillavgrateAvg;
        }

        public void setInfectillavgrateAvg(BigDecimal infectillavgrateAvg) {
            this.infectillavgrateAvg = infectillavgrateAvg;
        }

        public BigDecimal getInfectillavgrate() {
            return infectillavgrate;
        }

        public void setInfectillavgrate(BigDecimal infectillavgrate) {
            this.infectillavgrate = infectillavgrate;
        }

        public BigDecimal getInfectillavgrate1() {
            return infectillavgrate1;
        }

        public void setInfectillavgrate1(BigDecimal infectillavgrate1) {
            this.infectillavgrate1 = infectillavgrate1;
        }

        public BigDecimal getInfectillavgrate2() {
            return infectillavgrate2;
        }

        public void setInfectillavgrate2(BigDecimal infectillavgrate2) {
            this.infectillavgrate2 = infectillavgrate2;
        }

        public BigDecimal getInfectillavgrate3() {
            return infectillavgrate3;
        }

        public void setInfectillavgrate3(BigDecimal infectillavgrate3) {
            this.infectillavgrate3 = infectillavgrate3;
        }

        public BigDecimal getInfectillavgrate4() {
            return infectillavgrate4;
        }

        public void setInfectillavgrate4(BigDecimal infectillavgrate4) {
            this.infectillavgrate4 = infectillavgrate4;
        }

        public String getYear21() {
            return year21;
        }

        public void setYear21(String year21) {
            this.year21 = year21;
        }

        public BigDecimal getInfantmortalityrate() {
            return infantmortalityrate;
        }

        public void setInfantmortalityrate(BigDecimal infantmortalityrate) {
            this.infantmortalityrate = infantmortalityrate;
        }

        public String getYear22() {
            return year22;
        }

        public void setYear22(String year22) {
            this.year22 = year22;
        }

        public BigDecimal getInfantmortalityrate1() {
            return infantmortalityrate1;
        }

        public void setInfantmortalityrate1(BigDecimal infantmortalityrate1) {
            this.infantmortalityrate1 = infantmortalityrate1;
        }

        public String getYear23() {
            return year23;
        }

        public void setYear23(String year23) {
            this.year23 = year23;
        }

        public BigDecimal getInfantmortalityrate2() {
            return infantmortalityrate2;
        }

        public void setInfantmortalityrate2(BigDecimal infantmortalityrate2) {
            this.infantmortalityrate2 = infantmortalityrate2;
        }

        public String getYear38() {
            return year38;
        }

        public void setYear38(String year38) {
            this.year38 = year38;
        }

        public BigDecimal getUnderfivemortalityrate() {
            return underfivemortalityrate;
        }

        public void setUnderfivemortalityrate(BigDecimal underfivemortalityrate) {
            this.underfivemortalityrate = underfivemortalityrate;
        }

        public String getYear39() {
            return year39;
        }

        public void setYear39(String year39) {
            this.year39 = year39;
        }

        public BigDecimal getUnderfivemortalityrate1() {
            return underfivemortalityrate1;
        }

        public void setUnderfivemortalityrate1(BigDecimal underfivemortalityrate1) {
            this.underfivemortalityrate1 = underfivemortalityrate1;
        }

        public String getYear40() {
            return year40;
        }

        public void setYear40(String year40) {
            this.year40 = year40;
        }

        public BigDecimal getUnderfivemortalityrate2() {
            return underfivemortalityrate2;
        }

        public void setUnderfivemortalityrate2(BigDecimal underfivemortalityrate2) {
            this.underfivemortalityrate2 = underfivemortalityrate2;
        }

        public String getYear24() {
            return year24;
        }

        public void setYear24(String year24) {
            this.year24 = year24;
        }

        public BigDecimal getMaternalmortalityrate() {
            return maternalmortalityrate;
        }

        public void setMaternalmortalityrate(BigDecimal maternalmortalityrate) {
            this.maternalmortalityrate = maternalmortalityrate;
        }

        public String getYear25() {
            return year25;
        }

        public void setYear25(String year25) {
            this.year25 = year25;
        }

        public BigDecimal getMaternalmortalityrate1() {
            return maternalmortalityrate1;
        }

        public void setMaternalmortalityrate1(BigDecimal maternalmortalityrate1) {
            this.maternalmortalityrate1 = maternalmortalityrate1;
        }

        public String getYear26() {
            return year26;
        }

        public void setYear26(String year26) {
            this.year26 = year26;
        }

        public BigDecimal getMaternalmortalityrate2() {
            return maternalmortalityrate2;
        }

        public void setMaternalmortalityrate2(BigDecimal maternalmortalityrate2) {
            this.maternalmortalityrate2 = maternalmortalityrate2;
        }

        public String getYear29() {
            return year29;
        }

        public void setYear29(String year29) {
            this.year29 = year29;
        }

        public BigDecimal getPerexpectlife() {
            return perexpectlife;
        }

        public void setPerexpectlife(BigDecimal perexpectlife) {
            this.perexpectlife = perexpectlife;
        }

        public String getYear30() {
            return year30;
        }

        public void setYear30(String year30) {
            this.year30 = year30;
        }

        public BigDecimal getPerexpectlife1() {
            return perexpectlife1;
        }

        public void setPerexpectlife1(BigDecimal perexpectlife1) {
            this.perexpectlife1 = perexpectlife1;
        }

        public String getYear31() {
            return year31;
        }

        public void setYear31(String year31) {
            this.year31 = year31;
        }

        public BigDecimal getPerexpectlife2() {
            return perexpectlife2;
        }

        public void setPerexpectlife2(BigDecimal perexpectlife2) {
            this.perexpectlife2 = perexpectlife2;
        }

        public BigDecimal getVaccinationcoveragerate() {
            return vaccinationcoveragerate;
        }

        public void setVaccinationcoveragerate(BigDecimal vaccinationcoveragerate) {
            this.vaccinationcoveragerate = vaccinationcoveragerate;
        }

        public BigDecimal getAgedchildrencardrate() {
            return agedchildrencardrate;
        }

        public void setAgedchildrencardrate(BigDecimal agedchildrencardrate) {
            this.agedchildrencardrate = agedchildrencardrate;
        }

        public BigDecimal getPopulationhealthmanagementrate() {
            return populationhealthmanagementrate;
        }

        public void setPopulationhealthmanagementrate(BigDecimal populationhealthmanagementrate) {
            this.populationhealthmanagementrate = populationhealthmanagementrate;
        }

        public BigDecimal getHealthexaminationcoveragerate() {
            return healthexaminationcoveragerate;
        }

        public void setHealthexaminationcoveragerate(BigDecimal healthexaminationcoveragerate) {
            this.healthexaminationcoveragerate = healthexaminationcoveragerate;
        }

        public String getYear41() {
            return year41;
        }

        public void setYear41(String year41) {
            this.year41 = year41;
        }

        public BigDecimal getDiabeticbloodsugarcontrolrate() {
            return diabeticbloodsugarcontrolrate;
        }

        public void setDiabeticbloodsugarcontrolrate(BigDecimal diabeticbloodsugarcontrolrate) {
            this.diabeticbloodsugarcontrolrate = diabeticbloodsugarcontrolrate;
        }

        public String getYear42() {
            return year42;
        }

        public void setYear42(String year42) {
            this.year42 = year42;
        }

        public BigDecimal getDiabeticbloodsugarcontrolrate1() {
            return diabeticbloodsugarcontrolrate1;
        }

        public void setDiabeticbloodsugarcontrolrate1(BigDecimal diabeticbloodsugarcontrolrate1) {
            this.diabeticbloodsugarcontrolrate1 = diabeticbloodsugarcontrolrate1;
        }

        public String getYear43() {
            return year43;
        }

        public void setYear43(String year43) {
            this.year43 = year43;
        }

        public BigDecimal getDiabeticbloodsugarcontrolrate2() {
            return diabeticbloodsugarcontrolrate2;
        }

        public void setDiabeticbloodsugarcontrolrate2(BigDecimal diabeticbloodsugarcontrolrate2) {
            this.diabeticbloodsugarcontrolrate2 = diabeticbloodsugarcontrolrate2;
        }

        public BigDecimal getNeuropathymanagementrate() {
            return neuropathymanagementrate;
        }

        public void setNeuropathymanagementrate(BigDecimal neuropathymanagementrate) {
            this.neuropathymanagementrate = neuropathymanagementrate;
        }

        public BigDecimal getDiseaseequipmentstandardrate() {
            return diseaseequipmentstandardrate;
        }

        public void setDiseaseequipmentstandardrate(BigDecimal diseaseequipmentstandardrate) {
            this.diseaseequipmentstandardrate = diseaseequipmentstandardrate;
        }

        public BigDecimal getBlooddonationrate() {
            return blooddonationrate;
        }

        public void setBlooddonationrate(BigDecimal blooddonationrate) {
            this.blooddonationrate = blooddonationrate;
        }

        public BigDecimal getSecondpolyclinic() {
            return secondpolyclinic;
        }

        public void setSecondpolyclinic(BigDecimal secondpolyclinic) {
            this.secondpolyclinic = secondpolyclinic;
        }

        public BigDecimal getInfectiousdiseasedepartments() {
            return infectiousdiseasedepartments;
        }

        public void setInfectiousdiseasedepartments(BigDecimal infectiousdiseasedepartments) {
            this.infectiousdiseasedepartments = infectiousdiseasedepartments;
        }

        public BigDecimal getHealthconstructionstandardrate() {
            return healthconstructionstandardrate;
        }

        public void setHealthconstructionstandardrate(BigDecimal healthconstructionstandardrate) {
            this.healthconstructionstandardrate = healthconstructionstandardrate;
        }

        public BigDecimal getInsectcontrolinstallationqualificationrate() {
            return insectcontrolinstallationqualificationrate;
        }

        public void setInsectcontrolinstallationqualificationrate(BigDecimal insectcontrolinstallationqualificationrate) {
            this.insectcontrolinstallationqualificationrate = insectcontrolinstallationqualificationrate;
        }

        public BigDecimal getPublictoilets() {
            return publictoilets;
        }

        public void setPublictoilets(BigDecimal publictoilets) {
            this.publictoilets = publictoilets;
        }

        public BigDecimal getSecondpublictoilets() {
            return secondpublictoilets;
        }

        public void setSecondpublictoilets(BigDecimal secondpublictoilets) {
            this.secondpublictoilets = secondpublictoilets;
        }

        public BigDecimal getAgriculturalproductsmarketsrate() {
            return agriculturalproductsmarketsrate;
        }

        public void setAgriculturalproductsmarketsrate(BigDecimal agriculturalproductsmarketsrate) {
            this.agriculturalproductsmarketsrate = agriculturalproductsmarketsrate;
        }

        public BigDecimal getApibelow100days() {
            return apibelow100days;
        }

        public void setApibelow100days(BigDecimal apibelow100days) {
            this.apibelow100days = apibelow100days;
        }

        public BigDecimal getDaytimenoiseaverage() {
            return daytimenoiseaverage;
        }

        public void setDaytimenoiseaverage(BigDecimal daytimenoiseaverage) {
            this.daytimenoiseaverage = daytimenoiseaverage;
        }

        public BigDecimal getUrbanwaterworks() {
            return urbanwaterworks;
        }

        public void setUrbanwaterworks(BigDecimal urbanwaterworks) {
            this.urbanwaterworks = urbanwaterworks;
        }

        public String getDicratpopulcationlevel() {
            return dicratpopulcationlevel;
        }

        public void setDicratpopulcationlevel(String dicratpopulcationlevel) {
            this.dicratpopulcationlevel = dicratpopulcationlevel;
        }

        public String getDicmosquitopopulcationlevel() {
            return dicmosquitopopulcationlevel;
        }

        public void setDicmosquitopopulcationlevel(String dicmosquitopopulcationlevel) {
            this.dicmosquitopopulcationlevel = dicmosquitopopulcationlevel;
        }

        public String getDicflypopulcationlevel() {
            return dicflypopulcationlevel;
        }

        public void setDicflypopulcationlevel(String dicflypopulcationlevel) {
            this.dicflypopulcationlevel = dicflypopulcationlevel;
        }

        public String getDiccockroachpopulcationlevel() {
            return diccockroachpopulcationlevel;
        }

        public void setDiccockroachpopulcationlevel(String diccockroachpopulcationlevel) {
            this.diccockroachpopulcationlevel = diccockroachpopulcationlevel;
        }

        public BigDecimal getTapwaterstandardrate() {
            return tapwaterstandardrate;
        }

        public void setTapwaterstandardrate(BigDecimal tapwaterstandardrate) {
            this.tapwaterstandardrate = tapwaterstandardrate;
        }

        public BigDecimal getStumyopia() {
            return stumyopia;
        }

        public void setStumyopia(BigDecimal stumyopia) {
            this.stumyopia = stumyopia;
        }

        public AgwsSanitationCountyEvaluation(String regionname) {
            this.regionname = regionname;
        }

        public static AgwsSanitationCountyEvaluation getOne() {
            // 以辽宁省大连市为例
            return new AgwsSanitationCountyEvaluation("210200000");
        }
    }
}
