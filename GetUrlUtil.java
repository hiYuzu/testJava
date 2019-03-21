import java.io.*;
import java.net.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 *
 * @author hiYuzu
 * @version V1.0
 * </p>
 * @description 获取指定页面上所有链接并保存
 * @date 2018/12/4 10:44
 */
public class GetUrlUtil {
    public static void main(String[] args) {
        URL url = null;
        URLConnection connection = null;
        BufferedReader br = null;
        PrintWriter pw = null;
        //url匹配规则
        String regex = "https://[\\w+\\.?/?]+\\.[A-Za-z]+";
        Pattern p = Pattern.compile(regex);
        try {
            //爬取的网址、这里爬取的是一个生物网站
            url = new URL("https://www.rndsystems.com/cn");
            connection = url.openConnection();
            //将爬取到的链接放到D盘的SiteURL文件中
            pw = new PrintWriter(new FileWriter("D:/SiteURL.txt"), true);
            br = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String buf = null;
            while ((buf = br.readLine()) != null) {
                Matcher buf_m = p.matcher(buf);
                while (buf_m.find()) {
                    pw.println(buf_m.group());
                }
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
            pw.close();
        }
    }
}
