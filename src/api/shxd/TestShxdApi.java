package api.shxd;

import cn.hutool.http.HttpUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yuzu
 * @version v1.0
 * @since 2023/8/17 15:41
 */
public class TestShxdApi {
    /**
     * 协议
     */
    private static final String SYS_PROTOCOL = "http";
    /**
     * IP地址
     */
    private static final String SYS_IP = "127.0.0.1";
    /**
     * 端口号
     */
    private static final String SYS_PORT = "8082";
    /**
     * 方法
     */
    private static final String SYS_METHOD = "/openApi/getData";
    /**
     * 请求头校验名称
     */
    private static final String IIG_HEADER = "IIG-AUTH";
    /**
     * 校验值
     */
    private static final String IIG_AUTH = "e6201fdf-4606-41b9-a65f-37cf0dce080b";

    /**
     * 测试入口
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        // 传参Map
        Map<String, Object> paramMap = new HashMap<>(6);
        // 备案日期范围（起始日期）（非必要）
        paramMap.put("recordStart", "2023-08-10");
        // 备案日期范围（结束日期）（非必要）
        paramMap.put("recordEnd", "2023-08-20");
        // 统一社会信用代码（非必要）
        paramMap.put("makeCompCode", "91370700MA7GPU5B4R");
        // 强制查询（非必要）
        paramMap.put("force", "0");
        // 超时时间
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
