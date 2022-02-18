import cn.hutool.core.io.FileUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import cn.hutool.json.JSONUtil;

/**
 * @author hiYuzu
 * @version V1.0
 * @date 2021/11/8 14:08
 */
public class MyQrCodeUtil {
    public static void main(String[] args) {
        QrContent qrContent = new QrContent("https://www.baidu.com/", "org00001", "org00002");
        String content = JSONUtil.toJsonStr(qrContent);
        System.out.println(content);
        QrConfig config = new QrConfig(300, 300);
        config.setMargin(1);
        QrCodeUtil.generate(content, config);
    }
    static class QrContent {
        private String url;
        private String orgCode;
        private String reportCode;

        public QrContent(String url, String orgCode, String reportCode) {
            this.url = url;
            this.orgCode = orgCode;
            this.reportCode = reportCode;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getOrgCode() {
            return orgCode;
        }

        public void setOrgCode(String orgCode) {
            this.orgCode = orgCode;
        }

        public String getReportCode() {
            return reportCode;
        }

        public void setReportCode(String reportCode) {
            this.reportCode = reportCode;
        }
    }
}
