package api.http;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;

/**
 * <p>HTTP DELETE接口请求测试</p>
 * <p>请将IP、端口、证件号码、授权码等替换为真实数据</p>
 * <p>JDK版本：1.8</p>
 *
 * @author hiYuzu
 * @version V1.0
 * @date 2022/9/22 19:08
 */
public class TestQuarantineRemove {
    public static void main(String[] args) throws Exception {
        // IP地址
        String ip = "188.1.128.218";
        // 端口
        String port = "8080";
        // 证件号码
        String identity = "370786199701155455";
        // 授权码
        String auth = "jlf5ydoq-u7dh-olrp-n2mk-a8lrc8q3nfkw";

        HttpURLConnection con;
        BufferedReader buffer;
        StringBuilder resultBuilder;
        try {
            // 解除隔离时间
            String removeTime = "2022-10-04 12:00:00";
            long removeTimestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(removeTime).getTime();
            URL url = new URL("http://"+ ip + ":" + port + "/history/openApi/quarantine/remove?identity=" + identity + "&removeTimestamp=" + removeTimestamp);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("DELETE");
            con.setRequestProperty("IIG-AUTH", auth);
            con.setConnectTimeout(3000);
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

