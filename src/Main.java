import cn.hutool.http.HttpUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hiYuzu
 * @version V1.0
 * @date 2022/3/10 16:07
 */
public class Main {
    public static void main(String[] args) throws Exception {
        Map<String, Object> paramMap = new HashMap<>(9);
        paramMap.put("recordStart", "2023");
        paramMap.put("recordEnd", "2023-04-20");
        paramMap.put("makeCompCode", "91370700MA7GPU5B4R");
        paramMap.put("force", "1");

        final int timeout = 20000;
        String responseBody = HttpUtil.createPost( "http://127.0.0.1:8082/openApi/getData")
                .header("IIG-AUTH", "e6201fdf-4606-41b9-a65f-37cf0dce080b")
                .form(paramMap)
                .timeout(timeout)
                .execute()
                .body();
        System.out.println(responseBody);
    }
}