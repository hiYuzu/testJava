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
 * <p>健康城市评价填报</p>
 * <p>JDK版本：1.8</p>
 *
 * @author yuzu
 * @version v1.0
 * @since 2023/3/2 10:37
 */
public class TestHealthCityInfo {
    private static final String SYS_PROTOCOL = "http";
    private static final String SYS_IP = "127.0.0.1";
    private static final String SYS_PORT = "80";
    private static final String SYS_METHOD = "/openApi/healthCity/healthCityInfo";
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
        String content = JSONUtil.toJsonStr(AgwsHealthCityEvaluationEntity.getOne());
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
     * 健康城市评价所有字段
     */
    public static class AgwsHealthCityEvaluationEntity {
        /**
         * 行政区划编码（9位码，补0），必填
         */
        private String regionname;
        /**
         * 有无农村
         * "01": 有农村；"02": 无农村
         */
        private String haverural;
        /**
         * 全年空气质量指数AQI>200的天数
         */
        private Integer heavyairpollutiondays;
        /**
         * 集中式饮用水水源地安全保障达标率
         */
        private BigDecimal watersourcestandardrate;
        /**
         * 常住人口
         * 指标12.2、13.2、24.2、25.2、26.2、34.2、40.2、42.2
         */
        private BigDecimal residepopulation;
        /**
         * 城区人口和暂住人口
         */
        private BigDecimal residepopulationall;
        /**
         * 上一年度常住人口数
         */
        private BigDecimal residepopulationlast;
        /**
         * 国家卫生县城（乡镇）占比
         */
        private BigDecimal nationalhealthcountryrate;
        /**
         * 年度按照规范要求进行管理的确诊严重精神障碍患者数
         */
        private BigDecimal neuropathymanagement;
        /**
         * 所有登记在册确诊严重精神障碍患者人数
         */
        private BigDecimal neuropathymanagementall;
        /**
         * 年度辖区内接受1次及以上随访0-6岁儿童数
         */
        private BigDecimal childhealthmanagement;
        /**
         * 年度辖区内0-6岁儿童数
         */
        private BigDecimal childhealthmanagementall;
        /**
         * 辖区内妊娠至产后28天内接受规定服务的总人数
         */
        private BigDecimal pregnantmanagement;
        /**
         * 该地同期年度内的总活产数
         */
        private BigDecimal pregnantmanagementall;
        /**
         * 年末全科医生数
         */
        private BigDecimal generaldoctorsall;
        /**
         * 年末专业公共卫生机构人员数
         */
        private BigDecimal publichealthperson;
        /**
         * 年末医疗卫生机构床位数
         */
        private BigDecimal medicalinstitutionbedsall;
        /**
         * 能够提供中医药服务的社区卫生服务机构、乡镇卫生院数量
         */
        private BigDecimal tradmedinstowns;
        /**
         * 当年社区卫生服务机构、乡镇卫生院总数
         */
        private BigDecimal tradmedinstownsall;
        /**
         * 能够提供中医药服务的村卫生室数量
         * 若无农村，则值应为0
         */
        private BigDecimal tradmedinsvillages;
        /**
         * 当年村卫生室总数
         * 若无农村，则值应为0
         */
        private BigDecimal tradmedinsvillagesall;
        /**
         * 人均预期寿命
         */
        private BigDecimal percapitalifeexpectancy;
        /**
         * 年内未满1岁婴儿死亡数
         */
        private BigDecimal infantmortality;
        /**
         * 同年活产儿总数
         */
        private BigDecimal infantmortalityall;
        /**
         * 同年活产儿总数
         */
        private BigDecimal infantmortalityalluf;
        /**
         * 同年活产儿总数
         */
        private BigDecimal infantmortalityallmm;
        /**
         * 同年5岁以下儿童死亡数
         */
        private BigDecimal underfivemortality;
        /**
         * 年内孕产妇死亡数
         */
        private BigDecimal maternalmortality;
        /**
         * 3-6岁组《国民体质测定标准》综合得分≥20分的人数+20-39岁组《国民体质测定标准》综合得分≥23分的人数+40-59岁组《国民体质测定标准》综合得分≥18分的人数+60-69岁组《国民体质测定标准》综合得分≥15分的人数
         */
        private BigDecimal qualifiedphysique;
        /**
         * 监测总人数（3-6岁和20-69岁）
         */
        private BigDecimal qualifiedphysiqueall;
        /**
         * 年度甲乙类传染病报告发病数
         */
        private BigDecimal classabcontagionattack;
        /**
         * 重大慢性病过早死亡率
         */
        private BigDecimal chronicdiseasedeathrate;
        /**
         * 18-50岁人群高血压患病率
         */
        private BigDecimal eighteenfiftyhypertensionbloodpressurerate;
        /**
         * 18-50岁常住人口高血压患者数
         */
        private BigDecimal eighteenfiftyhypertensionbloodpressure;
        /**
         * 同年18-50岁常住人口总数
         */
        private BigDecimal eighteenfiftyhypertensionbloodpressureall;
        /**
         * 肿瘤年龄标化发病率变化幅度
         */
        private BigDecimal tumourattackchangerangerate;
        /**
         * 调查居民中具备基本健康素养的人数
         */
        private BigDecimal healthquality;
        /**
         * 调查居民总人数
         */
        private BigDecimal healthqualityall;
        /**
         * 调查人群（15岁及以上）中吸烟者人数
         */
        private BigDecimal adultsmoking;
        /**
         * 调查总人数
         */
        private BigDecimal adultsmokingall;
        /**
         * 经常参加体育锻炼的人数
         */
        private BigDecimal exercisepeople;
        /**
         * 全年空气质量指数（AQI）≤100的天数
         */
        private Integer aqibelow100days;
        /**
         * 检测饮用水末梢水常规指标达标的水样数
         */
        private Integer standardtipwatersamples;
        /**
         * 检测饮用水末梢水的总水样数
         */
        private Integer totaldetectionwatersamples;
        /**
         * 区域内达标饮用水水源地个数
         */
        private Integer standardwatersources;
        /**
         * 区域内集中式饮用水水源地总数
         */
        private Integer potablewatersources;
        /**
         * 报告期内生活垃圾无害化处理量
         */
        private BigDecimal domesticgarbageharmlessthroughput;
        /**
         * 报告期内生活垃圾产生量
         */
        private BigDecimal domesticgarbageproduction;
        /**
         * 城市城区内独立式、活动式和附属式公共厕所总数
         */
        private Integer publictoilets;
        /**
         * 本地农村使用卫生厕所的户数
         * 若无农村，则值应为0
         */
        private Integer villageharmlesstoilethouseholds;
        /**
         * 城区公园绿地面积
         */
        private BigDecimal greenlandtotalarea;
        /**
         * 本地农村总户数
         * 若无农村，则值应为0
         */
        private BigDecimal censusregisterpopulation;
        /**
         * 主要病媒生物密度控制水平达到达到A级的街道数
         */
        private Integer levelapetscontrolstreets;
        /**
         * 主要病媒生物密度控制水平达到达到B级的街道数
         */
        private Integer levelbpetscontrolstreets;
        /**
         * 建成区街道总数
         */
        private Integer petscontrolstreets;
        /**
         * 在基本医保目录范围内，从职工基本医疗保险基金中支付的住院总费用
         */
        private BigDecimal employeehospitalizationreimbursement;
        /**
         * 目录范围内职工住院医疗总费用
         */
        private BigDecimal employeetotalhospitalizationexpenses;
        /**
         * 在基本医保目录范围内，从城乡居民基本医疗保险基金中支付的住院总费用
         */
        private BigDecimal ruralhospitalizationreimbursement;
        /**
         * 目录范围内城乡居民住院医疗总费用
         */
        private BigDecimal ruraltotalhospitalizationexpenses;
        /**
         * 体育场地面积（室外+室内）
         */
        private BigDecimal sportsarea;
        /**
         * 市域内社会体育指导员人数
         */
        private Integer socialsportsinstructors;
        /**
         * 重点行业接触职业病危害的劳动者在岗期间职业健康检查人数
         */
        private Integer lifebloodoccupationalhealthexaminations;
        /**
         * 应接受职业健康检查人数
         */
        private Integer occupationalhealthexaminations;
        /**
         * 当年辖区内组织食品抽样检验批次数
         */
        private Integer foodsupervisioninspectiontimes;
        /**
         * 学年体质综合评定总分≥80分学生数
         */
        private Integer studentexcellentphysiques;
        /**
         * 参加评定学生总数
         */
        private Integer studentphysiques;
        /**
         * 各类养老服务机构的床位总数
         */
        private Integer retirementbeds;
        /**
         * 当地60岁及以上老年人口数
         */
        private Integer oversixtypeople;
        /**
         * 辖区内健康社区数
         */
        private Integer healthcommunitites;
        /**
         * 辖区内社区总数
         */
        private Integer communititesall;
        /**
         * 城市城区面积
         */
        private BigDecimal healthcityarea;
        /**
         * 辖区内中学和小学中健康学校数
         */
        private Integer healthschools;
        /**
         * 辖区内所有中学和小学数量之和
         */
        private Integer middleprimaryschools;
        /**
         * 辖区内大型和中型企业中健康企业数
         */
        private Integer healthenterprises;
        /**
         * 辖区内所有大型和中型企业数量之和
         */
        private Integer largemediumenterprises;
        /**
         * 当年卫生健康支出
         */
        private BigDecimal healthexpenditure;
        /**
         * 当年财政支出
         */
        private BigDecimal financeexpenditure;
        /**
         * 当年肿瘤年龄标化发病率
         */
        private BigDecimal thisyeartumourattackrate;
        /**
         * 上年肿瘤年龄标化发病率
         */
        private BigDecimal lastyeartumourattackrate;
        /**
         * 在广播电台设有健康栏目个数
         */
        private Integer propagandahealthstations;
        /**
         * 在报纸期刊上设有健康栏目个数
         */
        private Integer propagandahealthsections;
        /**
         * 本市“全国志愿服务信息系统”中注册的志愿者总人数
         */
        private Integer volunteerregistered;
        /**
         * 开办健康主题网站或主页个数
         */
        private Integer propagandahealthwebsites;
        /**
         * 在电视台设有健康栏目个数
         */
        private Integer propagandahealthchannels;
        /**
         * 联系人
         */
        private String contactPerson;
        /**
         * 联系方式
         */
        private String contactWay;
        /**
         * 备注
         */
        private String remark;

        public String getRegionname() {
            return regionname;
        }

        public void setRegionname(String regionname) {
            this.regionname = regionname;
        }

        public Integer getHeavyairpollutiondays() {
            return heavyairpollutiondays;
        }

        public void setHeavyairpollutiondays(Integer heavyairpollutiondays) {
            this.heavyairpollutiondays = heavyairpollutiondays;
        }

        public BigDecimal getWatersourcestandardrate() {
            return watersourcestandardrate;
        }

        public void setWatersourcestandardrate(BigDecimal watersourcestandardrate) {
            this.watersourcestandardrate = watersourcestandardrate;
        }

        public BigDecimal getResidepopulation() {
            return residepopulation;
        }

        public void setResidepopulation(BigDecimal residepopulation) {
            this.residepopulation = residepopulation;
        }

        public BigDecimal getResidepopulationall() {
            return residepopulationall;
        }

        public void setResidepopulationall(BigDecimal residepopulationall) {
            this.residepopulationall = residepopulationall;
        }

        public BigDecimal getResidepopulationlast() {
            return residepopulationlast;
        }

        public void setResidepopulationlast(BigDecimal residepopulationlast) {
            this.residepopulationlast = residepopulationlast;
        }

        public BigDecimal getNationalhealthcountryrate() {
            return nationalhealthcountryrate;
        }

        public void setNationalhealthcountryrate(BigDecimal nationalhealthcountryrate) {
            this.nationalhealthcountryrate = nationalhealthcountryrate;
        }

        public BigDecimal getNeuropathymanagement() {
            return neuropathymanagement;
        }

        public void setNeuropathymanagement(BigDecimal neuropathymanagement) {
            this.neuropathymanagement = neuropathymanagement;
        }

        public BigDecimal getNeuropathymanagementall() {
            return neuropathymanagementall;
        }

        public void setNeuropathymanagementall(BigDecimal neuropathymanagementall) {
            this.neuropathymanagementall = neuropathymanagementall;
        }

        public BigDecimal getChildhealthmanagement() {
            return childhealthmanagement;
        }

        public void setChildhealthmanagement(BigDecimal childhealthmanagement) {
            this.childhealthmanagement = childhealthmanagement;
        }

        public BigDecimal getChildhealthmanagementall() {
            return childhealthmanagementall;
        }

        public void setChildhealthmanagementall(BigDecimal childhealthmanagementall) {
            this.childhealthmanagementall = childhealthmanagementall;
        }

        public BigDecimal getPregnantmanagement() {
            return pregnantmanagement;
        }

        public void setPregnantmanagement(BigDecimal pregnantmanagement) {
            this.pregnantmanagement = pregnantmanagement;
        }

        public BigDecimal getPregnantmanagementall() {
            return pregnantmanagementall;
        }

        public void setPregnantmanagementall(BigDecimal pregnantmanagementall) {
            this.pregnantmanagementall = pregnantmanagementall;
        }

        public BigDecimal getGeneraldoctorsall() {
            return generaldoctorsall;
        }

        public void setGeneraldoctorsall(BigDecimal generaldoctorsall) {
            this.generaldoctorsall = generaldoctorsall;
        }

        public BigDecimal getPublichealthperson() {
            return publichealthperson;
        }

        public void setPublichealthperson(BigDecimal publichealthperson) {
            this.publichealthperson = publichealthperson;
        }

        public BigDecimal getMedicalinstitutionbedsall() {
            return medicalinstitutionbedsall;
        }

        public void setMedicalinstitutionbedsall(BigDecimal medicalinstitutionbedsall) {
            this.medicalinstitutionbedsall = medicalinstitutionbedsall;
        }

        public BigDecimal getTradmedinstowns() {
            return tradmedinstowns;
        }

        public void setTradmedinstowns(BigDecimal tradmedinstowns) {
            this.tradmedinstowns = tradmedinstowns;
        }

        public BigDecimal getTradmedinstownsall() {
            return tradmedinstownsall;
        }

        public void setTradmedinstownsall(BigDecimal tradmedinstownsall) {
            this.tradmedinstownsall = tradmedinstownsall;
        }

        public BigDecimal getTradmedinsvillages() {
            return tradmedinsvillages;
        }

        public void setTradmedinsvillages(BigDecimal tradmedinsvillages) {
            this.tradmedinsvillages = tradmedinsvillages;
        }

        public BigDecimal getTradmedinsvillagesall() {
            return tradmedinsvillagesall;
        }

        public void setTradmedinsvillagesall(BigDecimal tradmedinsvillagesall) {
            this.tradmedinsvillagesall = tradmedinsvillagesall;
        }

        public BigDecimal getPercapitalifeexpectancy() {
            return percapitalifeexpectancy;
        }

        public void setPercapitalifeexpectancy(BigDecimal percapitalifeexpectancy) {
            this.percapitalifeexpectancy = percapitalifeexpectancy;
        }

        public BigDecimal getInfantmortality() {
            return infantmortality;
        }

        public void setInfantmortality(BigDecimal infantmortality) {
            this.infantmortality = infantmortality;
        }

        public BigDecimal getInfantmortalityall() {
            return infantmortalityall;
        }

        public void setInfantmortalityall(BigDecimal infantmortalityall) {
            this.infantmortalityall = infantmortalityall;
        }

        public BigDecimal getInfantmortalityalluf() {
            return infantmortalityalluf;
        }

        public void setInfantmortalityalluf(BigDecimal infantmortalityalluf) {
            this.infantmortalityalluf = infantmortalityalluf;
        }

        public BigDecimal getInfantmortalityallmm() {
            return infantmortalityallmm;
        }

        public void setInfantmortalityallmm(BigDecimal infantmortalityallmm) {
            this.infantmortalityallmm = infantmortalityallmm;
        }

        public BigDecimal getUnderfivemortality() {
            return underfivemortality;
        }

        public void setUnderfivemortality(BigDecimal underfivemortality) {
            this.underfivemortality = underfivemortality;
        }

        public BigDecimal getMaternalmortality() {
            return maternalmortality;
        }

        public void setMaternalmortality(BigDecimal maternalmortality) {
            this.maternalmortality = maternalmortality;
        }

        public BigDecimal getQualifiedphysique() {
            return qualifiedphysique;
        }

        public void setQualifiedphysique(BigDecimal qualifiedphysique) {
            this.qualifiedphysique = qualifiedphysique;
        }

        public BigDecimal getQualifiedphysiqueall() {
            return qualifiedphysiqueall;
        }

        public void setQualifiedphysiqueall(BigDecimal qualifiedphysiqueall) {
            this.qualifiedphysiqueall = qualifiedphysiqueall;
        }

        public BigDecimal getClassabcontagionattack() {
            return classabcontagionattack;
        }

        public void setClassabcontagionattack(BigDecimal classabcontagionattack) {
            this.classabcontagionattack = classabcontagionattack;
        }

        public BigDecimal getChronicdiseasedeathrate() {
            return chronicdiseasedeathrate;
        }

        public void setChronicdiseasedeathrate(BigDecimal chronicdiseasedeathrate) {
            this.chronicdiseasedeathrate = chronicdiseasedeathrate;
        }

        public BigDecimal getEighteenfiftyhypertensionbloodpressurerate() {
            return eighteenfiftyhypertensionbloodpressurerate;
        }

        public void setEighteenfiftyhypertensionbloodpressurerate(BigDecimal eighteenfiftyhypertensionbloodpressurerate) {
            this.eighteenfiftyhypertensionbloodpressurerate = eighteenfiftyhypertensionbloodpressurerate;
        }

        public BigDecimal getEighteenfiftyhypertensionbloodpressure() {
            return eighteenfiftyhypertensionbloodpressure;
        }

        public void setEighteenfiftyhypertensionbloodpressure(BigDecimal eighteenfiftyhypertensionbloodpressure) {
            this.eighteenfiftyhypertensionbloodpressure = eighteenfiftyhypertensionbloodpressure;
        }

        public BigDecimal getEighteenfiftyhypertensionbloodpressureall() {
            return eighteenfiftyhypertensionbloodpressureall;
        }

        public void setEighteenfiftyhypertensionbloodpressureall(BigDecimal eighteenfiftyhypertensionbloodpressureall) {
            this.eighteenfiftyhypertensionbloodpressureall = eighteenfiftyhypertensionbloodpressureall;
        }

        public BigDecimal getTumourattackchangerangerate() {
            return tumourattackchangerangerate;
        }

        public void setTumourattackchangerangerate(BigDecimal tumourattackchangerangerate) {
            this.tumourattackchangerangerate = tumourattackchangerangerate;
        }

        public BigDecimal getHealthquality() {
            return healthquality;
        }

        public void setHealthquality(BigDecimal healthquality) {
            this.healthquality = healthquality;
        }

        public BigDecimal getHealthqualityall() {
            return healthqualityall;
        }

        public void setHealthqualityall(BigDecimal healthqualityall) {
            this.healthqualityall = healthqualityall;
        }

        public BigDecimal getAdultsmoking() {
            return adultsmoking;
        }

        public void setAdultsmoking(BigDecimal adultsmoking) {
            this.adultsmoking = adultsmoking;
        }

        public BigDecimal getAdultsmokingall() {
            return adultsmokingall;
        }

        public void setAdultsmokingall(BigDecimal adultsmokingall) {
            this.adultsmokingall = adultsmokingall;
        }

        public BigDecimal getExercisepeople() {
            return exercisepeople;
        }

        public void setExercisepeople(BigDecimal exercisepeople) {
            this.exercisepeople = exercisepeople;
        }

        public Integer getAqibelow100days() {
            return aqibelow100days;
        }

        public void setAqibelow100days(Integer aqibelow100days) {
            this.aqibelow100days = aqibelow100days;
        }

        public Integer getStandardtipwatersamples() {
            return standardtipwatersamples;
        }

        public void setStandardtipwatersamples(Integer standardtipwatersamples) {
            this.standardtipwatersamples = standardtipwatersamples;
        }

        public Integer getTotaldetectionwatersamples() {
            return totaldetectionwatersamples;
        }

        public void setTotaldetectionwatersamples(Integer totaldetectionwatersamples) {
            this.totaldetectionwatersamples = totaldetectionwatersamples;
        }

        public Integer getStandardwatersources() {
            return standardwatersources;
        }

        public void setStandardwatersources(Integer standardwatersources) {
            this.standardwatersources = standardwatersources;
        }

        public Integer getPotablewatersources() {
            return potablewatersources;
        }

        public void setPotablewatersources(Integer potablewatersources) {
            this.potablewatersources = potablewatersources;
        }

        public BigDecimal getDomesticgarbageharmlessthroughput() {
            return domesticgarbageharmlessthroughput;
        }

        public void setDomesticgarbageharmlessthroughput(BigDecimal domesticgarbageharmlessthroughput) {
            this.domesticgarbageharmlessthroughput = domesticgarbageharmlessthroughput;
        }

        public BigDecimal getDomesticgarbageproduction() {
            return domesticgarbageproduction;
        }

        public void setDomesticgarbageproduction(BigDecimal domesticgarbageproduction) {
            this.domesticgarbageproduction = domesticgarbageproduction;
        }

        public Integer getPublictoilets() {
            return publictoilets;
        }

        public void setPublictoilets(Integer publictoilets) {
            this.publictoilets = publictoilets;
        }

        public Integer getVillageharmlesstoilethouseholds() {
            return villageharmlesstoilethouseholds;
        }

        public void setVillageharmlesstoilethouseholds(Integer villageharmlesstoilethouseholds) {
            this.villageharmlesstoilethouseholds = villageharmlesstoilethouseholds;
        }

        public BigDecimal getGreenlandtotalarea() {
            return greenlandtotalarea;
        }

        public void setGreenlandtotalarea(BigDecimal greenlandtotalarea) {
            this.greenlandtotalarea = greenlandtotalarea;
        }

        public BigDecimal getCensusregisterpopulation() {
            return censusregisterpopulation;
        }

        public void setCensusregisterpopulation(BigDecimal censusregisterpopulation) {
            this.censusregisterpopulation = censusregisterpopulation;
        }

        public Integer getLevelapetscontrolstreets() {
            return levelapetscontrolstreets;
        }

        public void setLevelapetscontrolstreets(Integer levelapetscontrolstreets) {
            this.levelapetscontrolstreets = levelapetscontrolstreets;
        }

        public Integer getLevelbpetscontrolstreets() {
            return levelbpetscontrolstreets;
        }

        public void setLevelbpetscontrolstreets(Integer levelbpetscontrolstreets) {
            this.levelbpetscontrolstreets = levelbpetscontrolstreets;
        }

        public Integer getPetscontrolstreets() {
            return petscontrolstreets;
        }

        public void setPetscontrolstreets(Integer petscontrolstreets) {
            this.petscontrolstreets = petscontrolstreets;
        }

        public BigDecimal getEmployeehospitalizationreimbursement() {
            return employeehospitalizationreimbursement;
        }

        public void setEmployeehospitalizationreimbursement(BigDecimal employeehospitalizationreimbursement) {
            this.employeehospitalizationreimbursement = employeehospitalizationreimbursement;
        }

        public BigDecimal getEmployeetotalhospitalizationexpenses() {
            return employeetotalhospitalizationexpenses;
        }

        public void setEmployeetotalhospitalizationexpenses(BigDecimal employeetotalhospitalizationexpenses) {
            this.employeetotalhospitalizationexpenses = employeetotalhospitalizationexpenses;
        }

        public BigDecimal getRuralhospitalizationreimbursement() {
            return ruralhospitalizationreimbursement;
        }

        public void setRuralhospitalizationreimbursement(BigDecimal ruralhospitalizationreimbursement) {
            this.ruralhospitalizationreimbursement = ruralhospitalizationreimbursement;
        }

        public BigDecimal getRuraltotalhospitalizationexpenses() {
            return ruraltotalhospitalizationexpenses;
        }

        public void setRuraltotalhospitalizationexpenses(BigDecimal ruraltotalhospitalizationexpenses) {
            this.ruraltotalhospitalizationexpenses = ruraltotalhospitalizationexpenses;
        }

        public BigDecimal getSportsarea() {
            return sportsarea;
        }

        public void setSportsarea(BigDecimal sportsarea) {
            this.sportsarea = sportsarea;
        }

        public Integer getSocialsportsinstructors() {
            return socialsportsinstructors;
        }

        public void setSocialsportsinstructors(Integer socialsportsinstructors) {
            this.socialsportsinstructors = socialsportsinstructors;
        }

        public Integer getLifebloodoccupationalhealthexaminations() {
            return lifebloodoccupationalhealthexaminations;
        }

        public void setLifebloodoccupationalhealthexaminations(Integer lifebloodoccupationalhealthexaminations) {
            this.lifebloodoccupationalhealthexaminations = lifebloodoccupationalhealthexaminations;
        }

        public Integer getOccupationalhealthexaminations() {
            return occupationalhealthexaminations;
        }

        public void setOccupationalhealthexaminations(Integer occupationalhealthexaminations) {
            this.occupationalhealthexaminations = occupationalhealthexaminations;
        }

        public Integer getFoodsupervisioninspectiontimes() {
            return foodsupervisioninspectiontimes;
        }

        public void setFoodsupervisioninspectiontimes(Integer foodsupervisioninspectiontimes) {
            this.foodsupervisioninspectiontimes = foodsupervisioninspectiontimes;
        }

        public Integer getStudentexcellentphysiques() {
            return studentexcellentphysiques;
        }

        public void setStudentexcellentphysiques(Integer studentexcellentphysiques) {
            this.studentexcellentphysiques = studentexcellentphysiques;
        }

        public Integer getStudentphysiques() {
            return studentphysiques;
        }

        public void setStudentphysiques(Integer studentphysiques) {
            this.studentphysiques = studentphysiques;
        }

        public Integer getRetirementbeds() {
            return retirementbeds;
        }

        public void setRetirementbeds(Integer retirementbeds) {
            this.retirementbeds = retirementbeds;
        }

        public Integer getOversixtypeople() {
            return oversixtypeople;
        }

        public void setOversixtypeople(Integer oversixtypeople) {
            this.oversixtypeople = oversixtypeople;
        }

        public Integer getHealthcommunitites() {
            return healthcommunitites;
        }

        public void setHealthcommunitites(Integer healthcommunitites) {
            this.healthcommunitites = healthcommunitites;
        }

        public Integer getCommunititesall() {
            return communititesall;
        }

        public void setCommunititesall(Integer communititesall) {
            this.communititesall = communititesall;
        }

        public BigDecimal getHealthcityarea() {
            return healthcityarea;
        }

        public void setHealthcityarea(BigDecimal healthcityarea) {
            this.healthcityarea = healthcityarea;
        }

        public Integer getHealthschools() {
            return healthschools;
        }

        public void setHealthschools(Integer healthschools) {
            this.healthschools = healthschools;
        }

        public Integer getMiddleprimaryschools() {
            return middleprimaryschools;
        }

        public void setMiddleprimaryschools(Integer middleprimaryschools) {
            this.middleprimaryschools = middleprimaryschools;
        }

        public Integer getHealthenterprises() {
            return healthenterprises;
        }

        public void setHealthenterprises(Integer healthenterprises) {
            this.healthenterprises = healthenterprises;
        }

        public Integer getLargemediumenterprises() {
            return largemediumenterprises;
        }

        public void setLargemediumenterprises(Integer largemediumenterprises) {
            this.largemediumenterprises = largemediumenterprises;
        }

        public BigDecimal getHealthexpenditure() {
            return healthexpenditure;
        }

        public void setHealthexpenditure(BigDecimal healthexpenditure) {
            this.healthexpenditure = healthexpenditure;
        }

        public BigDecimal getFinanceexpenditure() {
            return financeexpenditure;
        }

        public void setFinanceexpenditure(BigDecimal financeexpenditure) {
            this.financeexpenditure = financeexpenditure;
        }

        public BigDecimal getThisyeartumourattackrate() {
            return thisyeartumourattackrate;
        }

        public void setThisyeartumourattackrate(BigDecimal thisyeartumourattackrate) {
            this.thisyeartumourattackrate = thisyeartumourattackrate;
        }

        public BigDecimal getLastyeartumourattackrate() {
            return lastyeartumourattackrate;
        }

        public void setLastyeartumourattackrate(BigDecimal lastyeartumourattackrate) {
            this.lastyeartumourattackrate = lastyeartumourattackrate;
        }

        public Integer getPropagandahealthstations() {
            return propagandahealthstations;
        }

        public void setPropagandahealthstations(Integer propagandahealthstations) {
            this.propagandahealthstations = propagandahealthstations;
        }

        public Integer getPropagandahealthsections() {
            return propagandahealthsections;
        }

        public void setPropagandahealthsections(Integer propagandahealthsections) {
            this.propagandahealthsections = propagandahealthsections;
        }

        public Integer getVolunteerregistered() {
            return volunteerregistered;
        }

        public void setVolunteerregistered(Integer volunteerregistered) {
            this.volunteerregistered = volunteerregistered;
        }

        public Integer getPropagandahealthwebsites() {
            return propagandahealthwebsites;
        }

        public void setPropagandahealthwebsites(Integer propagandahealthwebsites) {
            this.propagandahealthwebsites = propagandahealthwebsites;
        }

        public Integer getPropagandahealthchannels() {
            return propagandahealthchannels;
        }

        public void setPropagandahealthchannels(Integer propagandahealthchannels) {
            this.propagandahealthchannels = propagandahealthchannels;
        }

        public String getContactPerson() {
            return contactPerson;
        }

        public void setContactPerson(String contactPerson) {
            this.contactPerson = contactPerson;
        }

        public String getContactWay() {
            return contactWay;
        }

        public void setContactWay(String contactWay) {
            this.contactWay = contactWay;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getHaverural() {
            return haverural;
        }

        public void setHaverural(String haverural) {
            this.haverural = haverural;
        }

        public AgwsHealthCityEvaluationEntity(String regionname) {
            this.regionname = regionname;
        }

        public static AgwsHealthCityEvaluationEntity getOne() {
            // 以山东省潍坊市为例
            return new AgwsHealthCityEvaluationEntity("370700000");
        }
    }
}
