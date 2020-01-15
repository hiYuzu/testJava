package kyqtask;

import java.util.Scanner;

/**
 * 输入一个字符串，判断字符串是否属于“L语言”，返回“yes”或者“no”。
 * L语言：由任意个数（包括 0）的字母‘a’和奇数个数的字母‘b’组成。
 *
 * <p>Q1 : Write a Java program that can take as input any strings built
 * with the alphabet described above and answer yes or no if that string
 * belongs to the language L.
 *
 * @author hiYuzu
 * @version V1.0
 * @date 2019/12/11 8:12
 */
public class Problem1 {
    public static void main(String[] args) {
        try {
            Scanner reader = new Scanner(System.in);
            while (true) {
                String str = reader.nextLine();
                int num = 0;
                for (int i = 0; i < str.length(); i++) {
                    char c = str.charAt(i);
                    if (c == 'b') {
                        num++;
                    }
                }
                if (num % 2 == 1) {
                    System.out.println("yes");
                } else {
                    System.out.println("no");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
