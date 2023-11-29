package api.bjcdc;

import cn.hutool.http.HttpUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yuzu
 * @version v1.0
 * @since 2023/8/21 18:09
 */
public class TestHimwi {
    private static final String SYS_PROTOCOL = "http";
    private static final String SYS_IP_LOCAL = "127.0.0.1";
    private static final String SYS_PORT = "8080";
    private static final String SYS_METHOD_ILI = "/himwi-back/openApi/covidIli/allDataOfYear";
    private static final String SYS_METHOD_SARI = "/himwi-back/openApi/covidSari/allDataOfYear";
    private static final String IIG_HEADER = "IIG-AUTH";
    private static final String IIG_AUTH = "052de5ec-8396-4852-8987-51de0cc3dd3b";

    /**
     * 测试入口
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        // 传参Map
        Map<String, Object> paramMap = new HashMap<>(5);
        paramMap.put("year", 2023);
        paramMap.put("auditStatus", "1");
        paramMap.put("type", "1");

        final int timeout = 20000;
        String responseBody = HttpUtil.createPost(SYS_PROTOCOL + "://" + SYS_IP_LOCAL + ":" + SYS_PORT + SYS_METHOD_ILI)
                .header(IIG_HEADER, IIG_AUTH)
                .form(paramMap)
                .timeout(timeout)
                .execute()
                .body();
        System.out.println(responseBody);
    }
}
