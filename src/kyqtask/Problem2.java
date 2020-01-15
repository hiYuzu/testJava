package kyqtask;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * 线程版
 * @author hiYuzu
 * @version V1.0
 * @date 2019/12/11 9:59
 */
public class Problem2 {
    private static final String PATTERN = ".*aab.*";

    public static void main(String[] args) {
        ArrayList<String> arrayList = Problem1File.readFileByLine();
        for (String str : arrayList) {
            if (Pattern.matches(PATTERN, str)) {
                System.out.println("{" + str + "}∈E");
            } else {
                System.out.println("{" + str + "}∉E");
            }
        }
    }
}
