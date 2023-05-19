package bjcdc;

import cn.hutool.http.HttpUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hiYuzu
 * @version v2.0
 * @date 2023/4/14 17:05
 */
public class TestYqjc {
    private static final String SYS_PROTOCOL = "http";
    private static final String SYS_IP = "192.103.31.114";
    private static final String SYS_IP_LOCAL = "127.0.0.1";
    private static final String SYS_PORT = "8091";
    /**
     * 统计接口方法
     */
    private static final String SYS_METHOD = "/openApi/getYqjcData";
    /**
     * 个案接口方法
     */
    private static final String SYS_METHOD2 = "/openApi/getYqjcDetail";
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
        paramMap.put("startDate", "2023-05-18");
        paramMap.put("endDate", "2023-05-18");

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
