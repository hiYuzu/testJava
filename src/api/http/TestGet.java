package api.http;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.http.Method;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

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
        JSONObject test = new JSONObject();
        test.put("token", "1token1");
        test.put("clientId", "2clientId2");
        test.put("clientSecret", "3clientSecret3");
        System.out.println(test);
        String clientId = "2clientId2";
        String token = "1token1";
        String clientSecret = "3clientSecret3";
        System.out.println("{\"clientId\":\"" + clientId + "\",\"clientSecret\":\"" + clientSecret + "\",\"token\":\"" + token + "\"}");
        // IP地址
        String ip = "188.1.128.218";
        // 端口
        String port = "8080";
        // 授权码
        String auth = "jlf5ydoq-u7dh-olrp-n2mk-a8lrc8q3nfkw";
        // 查询类型
        String codeType = "popcode";
        // 证件号码
        String identity = "370786199701155455";
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

    public static void commonGet() {

    }

    public static void bodyGet() {
        String ip = "10.41.10.21";
        String port = "8082";
        String sysSampleNo = "360002400001";
        Map<String, String> body = new HashMap<>();
        body.put("sysSampleNo", sysSampleNo);
        String bodyData = JSONUtil.toJsonStr(body);
        HttpURLConnection con;
        BufferedReader buffer;
        StringBuilder resultBuilder;
        try {
            URL url = new URL("http://"+ ip + ":" + port + "/szpb/open/api/v1/getPathogenyData");
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setDoOutput(true);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Content-Length", String.valueOf(bodyData.length()));
            try (OutputStream os = con.getOutputStream()) {
                // 当doOutput为true时，HttpURLConnection.class会在getOutputStream0()方法中将GET换为POST
                // 写入请求体数据
                os.write(bodyData.getBytes());
                os.flush();
            }
            // 将上面的POST通过反射换回GET
            if (Method.POST.name().equals(con.getRequestMethod())) {
                ReflectUtil.setFieldValue(con, "method", Method.GET.name());
            }
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

