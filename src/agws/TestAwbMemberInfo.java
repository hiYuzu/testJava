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
 * <p>爱卫会成员单位基本情况填报</p>
 * <p>JDK版本：1.8</p>
 *
 * @author sinosoft
 * @version V1.0
 * @date 2023/3/7 16:17
 */
public class TestAwbMemberInfo {
    private static final String SYS_PROTOCOL = "http";
    private static final String SYS_IP = "127.0.0.1";
    private static final String SYS_PORT = "80";
    private static final String SYS_METHOD = "/openApi/baseInfo/awbMemberInfo";
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
        String content = JSONUtil.toJsonStr(AgwsOrganizationDivisionEntity.getOne());
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
     * 爱卫会成员单位基本情况所有字段
     */
    public static class AgwsOrganizationDivisionEntity {
        /**
         * 成员单位
         */
        private String organizationname;
        /**
         * 负责人
         */
        private String leadername;
        /**
         * 爱卫会任职（字典）
         * 01：爱卫会主任
         * 02：爱卫会副主任
         * 03：爱卫会委员
         */
        private String position;
        /**
         * 联络员
         */
        private String liaison;
        /**
         * 办公电话
         */
        private String telephone;
        /**
         * 手机号码
         */
        private String cellphone;
        /**
         * 邮箱
         */
        private String email;

        public String getOrganizationname() {
            return organizationname;
        }

        public void setOrganizationname(String organizationname) {
            this.organizationname = organizationname;
        }

        public String getLeadername() {
            return leadername;
        }

        public void setLeadername(String leadername) {
            this.leadername = leadername;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getLiaison() {
            return liaison;
        }

        public void setLiaison(String liaison) {
            this.liaison = liaison;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getCellphone() {
            return cellphone;
        }

        public void setCellphone(String cellphone) {
            this.cellphone = cellphone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public AgwsOrganizationDivisionEntity(String organizationname) {
            // 以下是必填项
            this.organizationname = organizationname;
        }

        public static AgwsOrganizationDivisionEntity getOne() {
            return new AgwsOrganizationDivisionEntity("测试成员单位");
        }
    }
}

