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
 * <p>县年度信息</p>
 * <p>JDK版本：1.8</p>
 *
 * @author yuzu
 * @version v1.0
 * @since 2023/9/4 11:20
 */
public class TestCountyYearInfo {

    private static final String SYS_PROTOCOL = "http";
    private static final String SYS_IP = "127.0.0.1";
    private static final String SYS_PORT = "80";
    private static final String SYS_METHOD = "/openApi/yearInfo/county";
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
        String content = JSONUtil.toJsonStr(CountyYearInfoEntity.getOne());
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
     * 城市年度信息所有字段
     */
    public static class CountyYearInfoEntity {
        /**
         * 行政区划编码（9位码，补0），必填
         */
        private String dtAreaCode;
        /**
         * 类别
         * 国家卫生城市："1"
         * 省卫生城市："2"
         * 其他："9"
         */
        private String namecategorycode;
        /**
         * 建成区面积（平方公里）
         */
        private BigDecimal totalarea;
        /**
         * 辖区面积（平方公里）
         */
        private BigDecimal landarea;
        /**
         * 辖区年末户籍总人口（万人）
         */
        private BigDecimal censusregisterpopulation;
        /**
         * 辖区年末常住人口（万人）
         */
        private BigDecimal residepopulation;
        /**
         * 建成区年末常住人口（万人）
         */
        private BigDecimal citypopulation;
        /**
         * 街道办事处数
         */
        private BigDecimal streetofficeamount;
        /**
         * 社区居委会数
         */
        private Integer neighborhoodcommittees;
        /**
         * 乡镇数
         */
        private Integer dministrativevillages;
        /**
         * 村民委员会数
         */
        private Integer provincialhealthvillages;
        /**
         * 地区生产总值（万元，按辖区统计）
         */
        private BigDecimal lastyeargdp;
        /**
         * 人均地区生产总值（元，按辖区统计）
         */
        private BigDecimal lastyearpgdp;
        /**
         * 地方一般公共预算收入（万元，按辖区统计）
         */
        private BigDecimal lastyearrevenue;
        /**
         * 城镇居民人均可支配收入（元，按辖区统计）
         */
        private BigDecimal lastannualincome;
        /**
         * 农村居民人均可支配收入（元，按辖区统计）
         */
        private BigDecimal lastvillagecome;

        public String getDtAreaCode() {
            return dtAreaCode;
        }

        public void setDtAreaCode(String dtAreaCode) {
            this.dtAreaCode = dtAreaCode;
        }

        public String getNamecategorycode() {
            return namecategorycode;
        }

        public void setNamecategorycode(String namecategorycode) {
            this.namecategorycode = namecategorycode;
        }

        public BigDecimal getTotalarea() {
            return totalarea;
        }

        public void setTotalarea(BigDecimal totalarea) {
            this.totalarea = totalarea;
        }

        public BigDecimal getLandarea() {
            return landarea;
        }

        public void setLandarea(BigDecimal landarea) {
            this.landarea = landarea;
        }

        public BigDecimal getCensusregisterpopulation() {
            return censusregisterpopulation;
        }

        public void setCensusregisterpopulation(BigDecimal censusregisterpopulation) {
            this.censusregisterpopulation = censusregisterpopulation;
        }

        public BigDecimal getResidepopulation() {
            return residepopulation;
        }

        public void setResidepopulation(BigDecimal residepopulation) {
            this.residepopulation = residepopulation;
        }

        public BigDecimal getCitypopulation() {
            return citypopulation;
        }

        public void setCitypopulation(BigDecimal citypopulation) {
            this.citypopulation = citypopulation;
        }

        public BigDecimal getStreetofficeamount() {
            return streetofficeamount;
        }

        public void setStreetofficeamount(BigDecimal streetofficeamount) {
            this.streetofficeamount = streetofficeamount;
        }

        public Integer getNeighborhoodcommittees() {
            return neighborhoodcommittees;
        }

        public void setNeighborhoodcommittees(Integer neighborhoodcommittees) {
            this.neighborhoodcommittees = neighborhoodcommittees;
        }

        public Integer getDministrativevillages() {
            return dministrativevillages;
        }

        public void setDministrativevillages(Integer dministrativevillages) {
            this.dministrativevillages = dministrativevillages;
        }

        public Integer getProvincialhealthvillages() {
            return provincialhealthvillages;
        }

        public void setProvincialhealthvillages(Integer provincialhealthvillages) {
            this.provincialhealthvillages = provincialhealthvillages;
        }

        public BigDecimal getLastyeargdp() {
            return lastyeargdp;
        }

        public void setLastyeargdp(BigDecimal lastyeargdp) {
            this.lastyeargdp = lastyeargdp;
        }

        public BigDecimal getLastyearpgdp() {
            return lastyearpgdp;
        }

        public void setLastyearpgdp(BigDecimal lastyearpgdp) {
            this.lastyearpgdp = lastyearpgdp;
        }

        public BigDecimal getLastyearrevenue() {
            return lastyearrevenue;
        }

        public void setLastyearrevenue(BigDecimal lastyearrevenue) {
            this.lastyearrevenue = lastyearrevenue;
        }

        public BigDecimal getLastannualincome() {
            return lastannualincome;
        }

        public void setLastannualincome(BigDecimal lastannualincome) {
            this.lastannualincome = lastannualincome;
        }

        public BigDecimal getLastvillagecome() {
            return lastvillagecome;
        }

        public void setLastvillagecome(BigDecimal lastvillagecome) {
            this.lastvillagecome = lastvillagecome;
        }

        public static CountyYearInfoEntity getOne() {
            return new CountyYearInfoEntity();
        }
    }
}
