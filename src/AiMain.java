import java.util.Scanner;

/**
 * AI核心代码，估值一个亿
 *
 * @author plr
 * @version V1.0
 * @date 2020/1/15 11:02
 */
public class AiMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String str;
        while (true) {
            str = scanner.next();
            str = str.replace("吗", "").replace("?", "!").replace("？", "！");
            System.out.println(str);
        }
    }
}
