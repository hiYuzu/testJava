import cn.hutool.core.lang.UUID;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author hiYuzu
 * @version V1.0
 * @date 2022/3/10 16:07
 */
public class Main {
    public static void main(String[] args) {
        for (int i = 0; i < 9; i++) {
            System.out.println(UUID.fastUUID().toString(true));
        }
    }
}

