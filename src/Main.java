import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hiYuzu
 * @version V1.0
 * @date 2022/3/10 16:07
 */
public class Main {
    public static void main(String[] args) {
        String text = "aaaaaa";
        byte[] utf8Bytes = text.getBytes(StandardCharsets.UTF_8);
        String utf8Text = new String(utf8Bytes, StandardCharsets.UTF_8);
        String isoText = new String(utf8Bytes, StandardCharsets.ISO_8859_1);
        System.out.println("UTF-8:::" + utf8Text);
        System.out.println("ISO_8859_1:::" + isoText);
    }
}