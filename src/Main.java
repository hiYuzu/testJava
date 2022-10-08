import cn.hutool.core.date.DateUtil;
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
        Date oldDate = DateUtil.parseDate("2022-10-24 15:00:00");
        int monday = 2;
        int dayOfWeek = DateUtil.dayOfWeek(oldDate);
        int offset = dayOfWeek - monday;
        if (offset < 0) {
            offset = 6;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String ymdStr = simpleDateFormat.format(DateUtil.offsetDay(oldDate, -offset));
        System.out.println(ymdStr);
    }
}

