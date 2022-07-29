import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * <p>接口请求测试</p>
 * <p>JDK版本：1.8</p>
 *
 * @author sinosoft
 * @version V1.0
 * @date 2022/7/29 15:22
 */
public class Test {
    public static void main(String[] args) throws Exception {
        HttpURLConnection con;
        BufferedReader buffer;
        StringBuilder resultBuilder;
        try {
            URL url = new URL("http://188.2.44.39:8080/api/nucleic/getRecentNucleic?cardNo=130204197212193019");
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", "33330b6b90994dff9deae1061f7bcad8");
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

