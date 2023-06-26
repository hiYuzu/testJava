package bjcdc;

import cn.hutool.http.HttpUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yuzu
 * @version v1.0
 * @since 2023/6/11 13:45
 */
public class TestPathogen {
    private static final String SYS_PROTOCOL = "http";
    private static final String SYS_IP_LOCAL = "127.0.0.1";
    private static final String SYS_PORT = "8080";
    private static final String SYS_METHOD = "/pathogen-back/openApi/pathogen/allDataOfYear";
    private static final String IIG_HEADER = "IIG-AUTH";
    private static final String IIG_AUTH = "7ccebf7d-a5cf-449d-a12c-faca2741fbff";

    /**
     * 测试入口
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        // 传参Map
        Map<String, Object> paramMap = new HashMap<>(9);
        paramMap.put("year", 2023);
        paramMap.put("auditStatus", "1");

        final int timeout = 20000;
        String responseBody = HttpUtil.createPost(SYS_PROTOCOL + "://" + SYS_IP_LOCAL + ":" + SYS_PORT + SYS_METHOD)
                .header(IIG_HEADER, IIG_AUTH)
                .form(paramMap)
                .timeout(timeout)
                .execute()
                .body();
        System.out.println(responseBody);
    }
}
