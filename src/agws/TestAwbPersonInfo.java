package agws;

import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.KeyUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import org.bouncycastle.crypto.engines.SM2Engine;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>爱卫办人员信息填报</p>
 * <p>JDK版本：1.8</p>
 *
 * @author sinosoft
 * @version V1.0
 * @date 2023/3/7 16:41
 */
public class TestAwbPersonInfo {
    private static final String SYS_PROTOCOL = "http";
    private static final String SYS_IP = "127.0.0.1";
    private static final String SYS_PORT = "80";
    private static final String SYS_METHOD = "/openApi/baseInfo/awbPersonInfo";
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
        String content = JSONUtil.toJsonStr(AwbPersonInfoEntity.getOne());
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
     * 爱卫办人员信息所有字段
     */
    public static class AwbPersonInfoEntity {
        /**
         * 姓名
         */
        private String name;
        /**
         * 性别
         * 1：男
         * 2：女
         */
        private String gender;
        /**
         * 出生日期
         */
        private Date birthday;
        /**
         * 职务（字典）
         * 01：爱卫办主任
         * 02：爱卫办副主任
         * 03：工作人员
         */
        private String position;
        /**
         * 行政级别（字典）
         * 参考：TestAwbBaseInfo.java 爱卫办主任行政级别
         */
        private String administrationlevel;
        /**
         * 技术职称
         * 01：初级专家
         * 02：中级专家
         * 03：高级专家
         */
        private String technictitle;
        /**
         * 最高学历
         * 01：博士
         * 02：硕士
         * 04：本科
         * 05：专科
         * 06：高中
         * 07：初中
         * 08：小学
         */
        private String educationlevel;
        /**
         * 进入现编制机构时间（yyyy-MM）
         */
        private String authorizeddate;
        /**
         * 开始从事爱国卫生工作时间（yyyy-MM）
         */
        private String workdate;
        /**
         * 是否在编（字典）
         * 1：是
         * 2：否
         */
        private String isauthorized;
        /**
         * 是否在岗（字典）
         * 1：是
         * 2：否
         */
        private String isappointed;
        /**
         * 是否兼职（字典）
         * 1：是
         * 2：否
         */
        private String isconcurrentpost;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public Date getBirthday() {
            return birthday;
        }

        public void setBirthday(Date birthday) {
            this.birthday = birthday;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getAdministrationlevel() {
            return administrationlevel;
        }

        public void setAdministrationlevel(String administrationlevel) {
            this.administrationlevel = administrationlevel;
        }

        public String getTechnictitle() {
            return technictitle;
        }

        public void setTechnictitle(String technictitle) {
            this.technictitle = technictitle;
        }

        public String getEducationlevel() {
            return educationlevel;
        }

        public void setEducationlevel(String educationlevel) {
            this.educationlevel = educationlevel;
        }

        public String getAuthorizeddate() {
            return authorizeddate;
        }

        public void setAuthorizeddate(String authorizeddate) {
            this.authorizeddate = authorizeddate;
        }

        public String getWorkdate() {
            return workdate;
        }

        public void setWorkdate(String workdate) {
            this.workdate = workdate;
        }

        public String getIsauthorized() {
            return isauthorized;
        }

        public void setIsauthorized(String isauthorized) {
            this.isauthorized = isauthorized;
        }

        public String getIsappointed() {
            return isappointed;
        }

        public void setIsappointed(String isappointed) {
            this.isappointed = isappointed;
        }

        public String getIsconcurrentpost() {
            return isconcurrentpost;
        }

        public void setIsconcurrentpost(String isconcurrentpost) {
            this.isconcurrentpost = isconcurrentpost;
        }

        public AwbPersonInfoEntity(String name, String isauthorized, String isappointed) {
            // 以下是必填项
            this.name = name;
            this.isauthorized = isauthorized;
            this.isappointed = isappointed;
        }

        public static AwbPersonInfoEntity getOne() {
            return new AwbPersonInfoEntity("张三", "1", "1");
        }
    }
}

