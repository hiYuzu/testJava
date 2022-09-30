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
        String date = "2022-09-22";
        String dateTime = date + " 00:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date agoDate = sdf.parse(dateTime);
            Date nowDate = new Date();
            int day = Math.abs((int) ((nowDate.getTime() - agoDate.getTime()) / (1000 * 3600 * 24)));
            System.out.println(day);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

