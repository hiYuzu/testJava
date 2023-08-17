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
 * <p>控烟专项调查填报</p>
 * <p>JDK版本：1.8</p>
 *
 * @author yuzu
 * @version v1.0
 * @since 2023/6/5 15:55
 */
public class TestTobaccoSpecial {
    private static final String SYS_PROTOCOL = "http";
    private static final String SYS_IP = "127.0.0.1";
    private static final String SYS_PORT = "80";
    private static final String SYS_METHOD = "/openApi/tobacco/tobaccoSpecial";
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
        String content = JSONUtil.toJsonStr(AwbTobControlEntity.getOne());
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
     * 控烟专项调查所有字段
     */
    public static class AwbTobControlEntity {
        /**
         * 行政区划编码（9位码，补0），必填
         */
        private String regionname;
        /**
         * 调查年份（成人）
         */
        private Integer declaredyear;
        /**
         * 调查年份（青少年）
         */
        private Integer declaredyearyouth;
        /**
         * 参加调查的15岁及以上总人数
         */
        private Integer overfifteenamounts;  //15岁及以上总人数
        /**
         * 参加调查的15岁及以上现在吸烟者人数
         */
        private Integer overfifteensmokingnow;
        /**
         * 参加调查的15岁及以上现在不吸烟者人数
         */
        private Integer overfifteennosmokingnow;
        /**
         * 现在不吸烟者二手烟暴露人数
         */
        private Integer exposenosmoking;
        /**
         * 参加调查的15岁及以上戒烟者人数
         */
        private Integer overfifteenquitsmoking;
        /**
         * 参加调查的15岁及以上电子烟使用者人数
         */
        private Integer overfifteenecigarette;
        /**
         * 参加调查的15岁及以上成人看到过控烟信息的人数
         */
        private Integer overfifteentobaccocontrol;
        /**
         * 参加调查的15岁及以上成人看到过烟草广告或促销的人数
         */
        private Integer overfifteentobaccoad;
        /**
         * 参加调查的15岁及以上知晓吸烟危害的人数
         */
        private Integer overfifteenkonw;
        /**
         * 参加调查的15岁及以上现在吸烟者购买卷烟花费
         */
        private BigDecimal overfifteencigarettecost;
        /**
         * 参加调查的在校中学生总人数
         */
        private Integer studentamounts;
        /**
         * 参加调查的在校中学生现在吸烟者人数
         */
        private Integer studentsmokingamounts;
        /**
         * 参加调查的在校中学生电子烟使用者人数
         */
        private Integer studentecigaretteamounts;
        /**
         * 参加调查的在校中学生看到过控烟信息的人数
         */
        private Integer studenttobaccocontrol;
        /**
         * 参加调查的在校中学生看到过烟草广告或促销的人数
         */
        private Integer studenttobaccoad;

        public AwbTobControlEntity(String regionname) {
            this.regionname = regionname;
        }

        public static AwbTobControlEntity getOne() {
            // 以山东省潍坊市为例
            return new AwbTobControlEntity("370700000");
        }
    }
}
