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
 * <p>卫生乡镇评估填报</p>
 * <p>JDK版本：1.8</p>
 *
 * @author hiYuzu
 * @version V1.0
 * @date 2023/3/21 15:04
 */
public class TestSanCountryInfo {
    private static final String SYS_PROTOCOL = "http";
    private static final String SYS_IP = "127.0.0.1";
    private static final String SYS_PORT = "80";
    private static final String SYS_METHOD = "/openApi/sanEva/countryInfo";
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
        String content = JSONUtil.toJsonStr(SanCountryEvaEntity.getOne());
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
     * 卫生乡镇评估所有字段
     */
    public static class SanCountryEvaEntity {
        /**
         * 行政区划编码（9位码），必填
         */
        private String regionname;
        /**
         * 群众对卫生状况满意率
         */
        private BigDecimal urbanhealthsatisfactionrate;
        /**
         * 建有全民健身场地设施的社区比例
         */
        private BigDecimal facilitycoveragerate;
        /**
         * 经常参加体育锻炼人数的比例
         */
        private BigDecimal exercisepeoplerate;
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
         * 乡镇下水道管网覆盖率
         */
        private BigDecimal manholeintactrate;
        /**
         * 乡镇生活垃圾无害化处理率
         */
        private BigDecimal domesticgarbageharmlessdisposal;
        /**
         * 农村卫生厕所普及率
         */
        private BigDecimal healthcommunities;
        /**
         * 全省农村厕所卫生普及率平均水平
         */
        private BigDecimal healthunits;
        /**
         * 集中式饮用水水源地水质达标率
         */
        private BigDecimal potablewaterrate;
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
        private BigDecimal perhealthspend;
        /**
         * 学校眼保健操普及率
         */
        private BigDecimal perhealthspendrate;
        /**
         * 中小学生近视率-年份1
         */
        private String year1;
        /**
         * 中小学生近视率-年份1对应的值
         */
        private BigDecimal stumyopiarate;
        /**
         * 中小学生近视率-年份2
         */
        private String year2;
        /**
         * 中小学生近视率-年份2对应的值
         */
        private BigDecimal stumyopiarate1;
        /**
         * 中小学生近视率-年份3
         */
        private String year3;
        /**
         * 中小学生近视率-年份3对应的值
         */
        private BigDecimal stumyopiarate2;
        /**
         * 中小学生肥胖率-年份1
         */
        private String year4;
        /**
         * 中小学生肥胖率-年份1对应的值
         */
        private BigDecimal stuobesityrate;
        /**
         * 中小学生肥胖率-年份2
         */
        private String year5;
        /**
         * 中小学生肥胖率-年份2对应的值
         */
        private BigDecimal stuobesityrate1;
        /**
         * 中小学生肥胖率-年份3
         */
        private String year6;
        /**
         * 中小学生肥胖率-年份3对应的值
         */
        private BigDecimal stuobesityrate2;
        /**
         * 存在职业病目录所列职业病危害因素的企业职业病危害项目申报率
         */
        private BigDecimal workdiseaserate;
        /**
         * 适龄儿童免疫规划疫苗接种率
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
         * 严重精神障碍患者规范管理率
         */
        private BigDecimal neuropathymanagementrate;
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

        public BigDecimal getUrbanhealthsatisfactionrate() {
            return urbanhealthsatisfactionrate;
        }

        public void setUrbanhealthsatisfactionrate(BigDecimal urbanhealthsatisfactionrate) {
            this.urbanhealthsatisfactionrate = urbanhealthsatisfactionrate;
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

        public BigDecimal getManholeintactrate() {
            return manholeintactrate;
        }

        public void setManholeintactrate(BigDecimal manholeintactrate) {
            this.manholeintactrate = manholeintactrate;
        }

        public BigDecimal getDomesticgarbageharmlessdisposal() {
            return domesticgarbageharmlessdisposal;
        }

        public void setDomesticgarbageharmlessdisposal(BigDecimal domesticgarbageharmlessdisposal) {
            this.domesticgarbageharmlessdisposal = domesticgarbageharmlessdisposal;
        }

        public BigDecimal getHealthcommunities() {
            return healthcommunities;
        }

        public void setHealthcommunities(BigDecimal healthcommunities) {
            this.healthcommunities = healthcommunities;
        }

        public BigDecimal getHealthunits() {
            return healthunits;
        }

        public void setHealthunits(BigDecimal healthunits) {
            this.healthunits = healthunits;
        }

        public BigDecimal getPotablewaterrate() {
            return potablewaterrate;
        }

        public void setPotablewaterrate(BigDecimal potablewaterrate) {
            this.potablewaterrate = potablewaterrate;
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

        public BigDecimal getPerhealthspend() {
            return perhealthspend;
        }

        public void setPerhealthspend(BigDecimal perhealthspend) {
            this.perhealthspend = perhealthspend;
        }

        public BigDecimal getPerhealthspendrate() {
            return perhealthspendrate;
        }

        public void setPerhealthspendrate(BigDecimal perhealthspendrate) {
            this.perhealthspendrate = perhealthspendrate;
        }

        public String getYear1() {
            return year1;
        }

        public void setYear1(String year1) {
            this.year1 = year1;
        }

        public BigDecimal getStumyopiarate() {
            return stumyopiarate;
        }

        public void setStumyopiarate(BigDecimal stumyopiarate) {
            this.stumyopiarate = stumyopiarate;
        }

        public String getYear2() {
            return year2;
        }

        public void setYear2(String year2) {
            this.year2 = year2;
        }

        public BigDecimal getStumyopiarate1() {
            return stumyopiarate1;
        }

        public void setStumyopiarate1(BigDecimal stumyopiarate1) {
            this.stumyopiarate1 = stumyopiarate1;
        }

        public String getYear3() {
            return year3;
        }

        public void setYear3(String year3) {
            this.year3 = year3;
        }

        public BigDecimal getStumyopiarate2() {
            return stumyopiarate2;
        }

        public void setStumyopiarate2(BigDecimal stumyopiarate2) {
            this.stumyopiarate2 = stumyopiarate2;
        }

        public String getYear4() {
            return year4;
        }

        public void setYear4(String year4) {
            this.year4 = year4;
        }

        public BigDecimal getStuobesityrate() {
            return stuobesityrate;
        }

        public void setStuobesityrate(BigDecimal stuobesityrate) {
            this.stuobesityrate = stuobesityrate;
        }

        public String getYear5() {
            return year5;
        }

        public void setYear5(String year5) {
            this.year5 = year5;
        }

        public BigDecimal getStuobesityrate1() {
            return stuobesityrate1;
        }

        public void setStuobesityrate1(BigDecimal stuobesityrate1) {
            this.stuobesityrate1 = stuobesityrate1;
        }

        public String getYear6() {
            return year6;
        }

        public void setYear6(String year6) {
            this.year6 = year6;
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

        public BigDecimal getNeuropathymanagementrate() {
            return neuropathymanagementrate;
        }

        public void setNeuropathymanagementrate(BigDecimal neuropathymanagementrate) {
            this.neuropathymanagementrate = neuropathymanagementrate;
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

        public SanCountryEvaEntity(String regionname) {
            this.regionname = regionname;
        }

        public static SanCountryEvaEntity getOne() {
            // 以辽宁省大连市为例
            return new SanCountryEvaEntity("210200000");
        }
    }
}
