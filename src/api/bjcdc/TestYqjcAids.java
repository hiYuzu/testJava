package api.bjcdc;

import cn.hutool.http.HttpUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yuzu
 * @version v1.2
 * @since 2023/6/28 14:31
 */
public class TestYqjcAids {
    private static final String SYS_PROTOCOL = "http";
    private static final String SYS_IP = "192.103.31.114";
    private static final String SYS_PORT = "8091";
    /**
     * 个案接口方法
     */
    private static final String SYS_METHOD = "/openApi/getYqjcAids";
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
        Map<String, Object> paramMap = new HashMap<>(9);
        paramMap.put("startDate", "2024-03-26 10:00:00");
        paramMap.put("endDate", "2024-03-26 11:00:00");

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
