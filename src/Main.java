import cn.hutool.core.date.DateUtil;

import java.util.*;
import java.util.logging.Logger;

/**
 * @author hiYuzu
 * @version V1.0
 * @date 2024/1/9 16:40
 */
public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    public static void main(String[] args) throws Exception {
        Date date1 = DateUtil.parseDateTime("2025-03-27 15:32:56");
        Date date2 = DateUtil.parseDateTime("2026-02-04 13:32:56");
        logger.warning(String.valueOf(DateUtil.betweenDay(date1, date2, true)));
    }
}
