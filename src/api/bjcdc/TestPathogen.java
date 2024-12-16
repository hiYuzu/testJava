package api.bjcdc;

import cn.hutool.http.HttpUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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

        final int timeout = 20000;
        String responseBody = HttpUtil.createPost(SYS_PROTOCOL + "://" + SYS_IP_LOCAL + ":" + SYS_PORT + SYS_METHOD)
                .header(IIG_HEADER, IIG_AUTH)
                .form(paramMap)
                .timeout(timeout)
                .execute()
                .body();
        System.out.println(responseBody);
    }

    public static void main2(String[] args) throws Exception {
        try {
            // 1. 创建URL对象
            URL url = new URL(SYS_PROTOCOL + "://" + SYS_IP_LOCAL + ":" + SYS_PORT + SYS_METHOD);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // 2. 设置请求方法为POST
            connection.setRequestMethod("POST");
            connection.setRequestProperty(IIG_HEADER, IIG_AUTH);
            connection.setDoOutput(true);

            // 3. 设置请求头
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            // 4. URL编码表单数据
            String postData = "param1=" + URLEncoder.encode("value1", "UTF-8") + "&param2=" + URLEncoder.encode("value2", "UTF-8");

            // 5. 写入请求体
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = postData.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // 6. 读取响应
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println("Response: " + response);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
