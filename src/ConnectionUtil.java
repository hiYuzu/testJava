package src;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * <p>
 *
 * @author hiYuzu
 * @version V1.0
 * </p>
 * @description 获取网站页面数据
 * @date 2018/12/4 10:27
 */
public class ConnectionUtil {
    public static String connect(String address) {
        URL url = null;
        URLConnection connection = null;
        BufferedReader br = null;
        StringBuffer stringBuffer = null;
        try {
            stringBuffer = new StringBuffer();
            url = new URL(address);
            connection = url.openConnection();
            br = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String buf = null;
            while ((buf = br.readLine()) != null) {
                stringBuffer.append(buf, 0, buf.length());
                stringBuffer.append("\n");
            }
            System.out.println("爬取成功^_^");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return stringBuffer.toString();
    }
}
