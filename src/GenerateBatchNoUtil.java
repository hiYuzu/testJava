import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author hiYuzu
 * @version v1.0
 * @date 2022/5/28 15:24
 */
public class GenerateBatchNoUtil {
    private static final Map<String, AtomicInteger> ATOMIC_INTEGER_MAP = new HashMap<>();

    /**
     * 生成批次号
     *
     * @param type 类型
     * @return 批次号
     */
    public static String generate(String type) {
        DateTime dateTime = DateUtil.date();
        String dateStr = DateUtil.format(dateTime, DatePattern.PURE_DATE_PATTERN);
        AtomicInteger atomicInt = ATOMIC_INTEGER_MAP.get(dateStr + type);
        if (atomicInt == null) {
            atomicInt = new AtomicInteger(1);
            ATOMIC_INTEGER_MAP.put(dateStr + type, atomicInt);
            String oldDateStr = DateUtil.format(DateUtil.yesterday(), DatePattern.PURE_DATE_PATTERN);
            ATOMIC_INTEGER_MAP.remove(oldDateStr + type);
        }
        String currentNo = String.valueOf(atomicInt.getAndIncrement());
        /// 若需要补0，且最多6位，则：
//        String currentNo = String.format("%06d", atomicInt.getAndIncrement());
        return dateStr + currentNo;
    }
}
