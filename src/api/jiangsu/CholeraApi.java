package api.jiangsu;

import cn.hutool.http.HttpUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yuzu
 * @version v1.0
 * @since 2024/9/20 9:28
 */
public class CholeraApi {
    private static final String URL_1 = "http://192.168.50.151:9999/cholera/openApi/investigationApi/getAllBasicData";
    private static final String IIG_HEADER = "IIG-AUTH";
    private static final String IIG_AUTH = "83c6ef47-90af-4842-aa1a-90ad4fae577f";

    public static void main(String[] args) throws Exception {
        Map<String, Object> paramMap = new HashMap<>(9);
        paramMap.put("cardId", "emr_inf_report_id_1");

        final int timeout = 5000;
        String responseBody = HttpUtil.createPost(URL_1)
                .header(IIG_HEADER, IIG_AUTH)
                .form(paramMap)
                .timeout(timeout)
                .execute()
                .body();
        System.out.println(responseBody);
    }
}
