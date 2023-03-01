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
 * <p>接口请求测试</p>
 * <p>JDK版本：1.8</p>
 *
 * @author sinosoft
 * @version V1.0
 * @date 2023/2/19 14:51
 */
public class Test {
    private static final String SYS_PROTOCOL = "http";
    private static final String SYS_IP = "127.0.0.1";
    private static final String SYS_PORT = "80";
    private static final String SYS_METHOD = "/openApi/baseInfo/awbBaseInfo";
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
        String content = JSONUtil.toJsonStr(AwbBaseInfoEntity.getOne());
        // SM2加密
        byte [] publicKey = HexUtil.decodeHex(PUBLIC_KEY_STR);
        SM2 sm2 = SmUtil.sm2();
        sm2.setMode(SM2Engine.Mode.C1C2C3);
        sm2.setPublicKey(KeyUtil.generatePublicKey("SM2", publicKey));
        String result = sm2.encryptBase64(content, StandardCharsets.UTF_8, KeyType.PublicKey);
        System.out.println("密文：" + result);
        // 传参Map
        Map<String, Object> paramMap = new HashMap<>(5);
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
     * 爱卫办基本信息所有字段（不含附件）
     */
    public static class AwbBaseInfoEntity {
        /**
         * 填报时间
         */
        private String declareYear;
        /**
         * 办事机构名称
         */
        private String organization_name;
        /**
         * 机构性质（字典）
         */
        private String organization_type;
        /**
         * 机构行政级别（字典）
         */
        private String administration_level;
        /**
         * 行政主管部门（字典）
         */
        private String administration_department;
        /**
         * 邮编
         */
        private String postcode;
        /**
         * 通讯地址
         */
        private String address;
        /**
         * 联系电话
         */
        private String telephone;
        /**
         * 传真
         */
        private String fax;
        /**
         * 邮箱
         */
        private String email;
        /**
         * 爱卫会主任行政级别（字典）
         */
        private String director_level2;
        /**
         * 爱卫办主任行政级别（字典）
         */
        private String director_level;
        /**
         * 机构编制人数（人）
         */
        private Integer establishments;
        /**
         * 编内在岗人数（人）
         */
        private Integer dutyestablishments;
        /**
         * 实际在岗
         */
        private Integer reallyestablishments;
        /**
         * 办公用房面积（㎡）
         */
        private BigDecimal area;
        /**
         * 办公用车数（辆）
         */
        private Integer car;
        /**
         * 改厕责任部门
         */
        private String toilet_department;
        /**
         * 控烟行政主管部门
         */
        private String tobacco_department;
        /**
         * 具体承办工作内容
         */
        private String work_content;

        public String getDeclareYear() {
            return declareYear;
        }

        public void setDeclareYear(String declareYear) {
            this.declareYear = declareYear;
        }

        public String getOrganization_name() {
            return organization_name;
        }

        public void setOrganization_name(String organization_name) {
            this.organization_name = organization_name;
        }

        public String getOrganization_type() {
            return organization_type;
        }

        public void setOrganization_type(String organization_type) {
            this.organization_type = organization_type;
        }

        public String getAdministration_level() {
            return administration_level;
        }

        public void setAdministration_level(String administration_level) {
            this.administration_level = administration_level;
        }

        public String getAdministration_department() {
            return administration_department;
        }

        public void setAdministration_department(String administration_department) {
            this.administration_department = administration_department;
        }

        public String getPostcode() {
            return postcode;
        }

        public void setPostcode(String postcode) {
            this.postcode = postcode;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getFax() {
            return fax;
        }

        public void setFax(String fax) {
            this.fax = fax;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getDirector_level2() {
            return director_level2;
        }

        public void setDirector_level2(String director_level2) {
            this.director_level2 = director_level2;
        }

        public String getDirector_level() {
            return director_level;
        }

        public void setDirector_level(String director_level) {
            this.director_level = director_level;
        }

        public Integer getEstablishments() {
            return establishments;
        }

        public void setEstablishments(Integer establishments) {
            this.establishments = establishments;
        }

        public Integer getDutyestablishments() {
            return dutyestablishments;
        }

        public void setDutyestablishments(Integer dutyestablishments) {
            this.dutyestablishments = dutyestablishments;
        }

        public Integer getReallyestablishments() {
            return reallyestablishments;
        }

        public void setReallyestablishments(Integer reallyestablishments) {
            this.reallyestablishments = reallyestablishments;
        }

        public BigDecimal getArea() {
            return area;
        }

        public void setArea(BigDecimal area) {
            this.area = area;
        }

        public Integer getCar() {
            return car;
        }

        public void setCar(Integer car) {
            this.car = car;
        }

        public String getToilet_department() {
            return toilet_department;
        }

        public void setToilet_department(String toilet_department) {
            this.toilet_department = toilet_department;
        }

        public String getTobacco_department() {
            return tobacco_department;
        }

        public void setTobacco_department(String tobacco_department) {
            this.tobacco_department = tobacco_department;
        }

        public String getWork_content() {
            return work_content;
        }

        public void setWork_content(String work_content) {
            this.work_content = work_content;
        }

        public AwbBaseInfoEntity(String declareYear, String organization_name, String organization_type, String administration_level, Integer establishments, Integer dutyestablishments, Integer reallyestablishments) {
            // 以下为必填项
            this.declareYear = declareYear;
            this.organization_name = organization_name;
            this.organization_type = organization_type;
            this.administration_level = administration_level;
            this.establishments = establishments;
            this.dutyestablishments = dutyestablishments;
            this.reallyestablishments = reallyestablishments;
        }

        public static AwbBaseInfoEntity getOne() {
            return new AwbBaseInfoEntity("2022", "测试机构", "1", "1", 10, 10, 10);
        }
    }
}

