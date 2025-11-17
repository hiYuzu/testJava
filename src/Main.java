import cn.hutool.core.lang.UUID;
import cn.hutool.json.JSONUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author hiYuzu
 * @version V1.0
 * @date 2024/1/9 16:40
 */
public class Main {
    public static void main(String[] args) throws Exception {
        List<Double> a = new ArrayList<>();
        a.add(1D);
        a.add(2D);
        a.add(null);
        a.add(null);
        a.add(5D);
        System.out.println(a.get(0));
        System.out.println(a.get(1));
        System.out.println(a.get(2));
        System.out.println(a.get(3));
        System.out.println(a.get(4));
    }
}
