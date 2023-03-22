package http;

import cn.hutool.http.HttpUtil;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hiYuzu
 * @version v1.0
 * @date 2023/3/21 18:00
 */
public class TestPost {
    private static final String SYS_PROTOCOL = "http";
    private static final String SYS_IP = "127.0.0.1";
    private static final String SYS_PORT = "8090";
    private static final String SYS_METHOD = "/openApi/getFluData";
    private static final String IIG_HEADER = "IIG-AUTH";
    private static final String IIG_AUTH = "56bcd26b-8810-433f-9d8d-1c02688e8f71";

    /**
     * 测试入口
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        // 传参Map
        Map<String, Object> paramMap = new HashMap<>(3);
        paramMap.put("startDate", "2020-01-01 00:00:00");
        paramMap.put("endDate", "2020-01-01 23:59:59");
        paramMap.put("hospitalLevel", "0");
        paramMap.put("isCollege", "1");
        paramMap.put("isDetail", "1");
        paramMap.put("auditState", "1");

        final int timeout = 20000;
        String responseBody = HttpUtil.createPost(SYS_PROTOCOL + "://" + SYS_IP + ":" + SYS_PORT + SYS_METHOD)
                .header(IIG_HEADER, IIG_AUTH)
                .form(paramMap)
                .timeout(timeout)
                .execute()
                .body();
        System.out.println(responseBody);
    }
}
