package kyqtask;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 文件版
 * @author hiYuzu
 * @version V1.0
 * @date 2019/12/11 9:41
 */
public class Problem1File {

    private static final String FILE_PATH = "E:/file.txt";

    public static void main(String[] args) {
        ArrayList<String> arrayList = readFileByLine();
        for (String str : arrayList) {
            int num = 0;
            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);
                if (c == 'b') {num++;}
            }
            if (num % 2 == 1) {
                System.out.println(str + "：yes");
            } else {
                System.out.println(str + "：no");
            }
        }
    }

    static ArrayList<String> readFileByLine() {
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(FILE_PATH);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String str;
            while ((str = bufferedReader.readLine()) != null) {
                arrayList.add(str);
            }
            bufferedReader.close();
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arrayList;
    }
}
