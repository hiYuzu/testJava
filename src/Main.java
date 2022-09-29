import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

/**
 * @author hiYuzu
 * @version V1.0
 * @date 2022/3/10 16:07
 */
public class Main {
    public static void main(String[] args) {
        String identity = "110101196511231519";
        // 开始日期
        String startDate = "2022-09-22";
        // 分类
        String fl = "15";
        // 授权码
        String auth = "jlf5ydoq-u7dh-olrp-n2mk-a8lrc8q3nfkw";
        String url = "http://188.2.44.95:8085/openApi/getDetail?identity=" + identity + "&startDate=" + startDate + "&fl=" + fl;
        try (HttpResponse response = HttpUtil.createGet(url).header("IIG-AUTH", auth).setConnectionTimeout(20000).execute()) {
            String responseStr = response.body();
            JSONObject jsonObject = JSONUtil.parseObj(responseStr);
            if ("200".equals(jsonObject.getStr("code"))) {
                JSONObject data = jsonObject.getJSONObject("data");
                System.out.println(JSONUtil.toJsonPrettyStr(data));
            }
        }
    }

}

