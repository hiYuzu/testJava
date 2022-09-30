package http;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * <p>HTTP GET接口请求测试</p>
 * <p>请将IP、端口、证件号码、授权码等替换为真实数据</p>
 * <p>JDK版本：1.8</p>
 *
 * @author hiYuzu
 * @version V1.0
 * @date 2022/9/22 14:18
 */
public class TestGet {
    public static void main(String[] args) throws Exception {
        // IP地址
        String ip = "188.1.128.218";
        // 端口
        String port = "8084";
        // 授权码
        String auth = "jlf5ydoq-u7dh-olrp-n2mk-a8lrc8q3nfkw";
        // 查询类型
        String codeType = "popcode";
        // 证件号码
        String identity = "130182200509253912";
        // 开始日期
        String startDate = "2022-09-29";
        // 分类
        String fl = "60";

        HttpURLConnection con;
        BufferedReader buffer;
        StringBuilder resultBuilder;
        try {
            URL url = new URL("http://"+ ip + ":" + port + "/history/openApi/appeal/getPersonInfo?identity=" + identity + "&codeType=" + codeType);
            URL url2 = new URL("http://"+ ip + ":" + port + "/history/openApi/appeal/getDataDetail?identity=" + identity + "&fl=" + fl + "&startDate=" + startDate);
//            con = (HttpURLConnection) url.openConnection();
            con = (HttpURLConnection) url2.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("IIG-AUTH", auth);
            con.setDoOutput(true);
            con.setUseCaches(false);
            if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = con.getInputStream();
                resultBuilder = new StringBuilder();
                String line;
                buffer = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                while ((line = buffer.readLine()) != null) {
                    resultBuilder.append(line);
                }
                System.out.println(resultBuilder);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

