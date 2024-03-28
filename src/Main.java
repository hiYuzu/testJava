import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author hiYuzu
 * @version V1.0
 * @date 2024/1/9 16:40
 */
public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println(DateUtil.format(new Date(), DatePattern.createFormatter("yyyyMMdd'T'HHmmss")));
    }
}
