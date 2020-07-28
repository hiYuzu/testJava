import java.io.*;

/**
 * 合并TXT文档
 * @author hiYuzu
 * @version V1.0
 * @date 2020/7/28 13:34
 */
public class MergeTxt {
    public static void main(String[] args) throws IOException {
        String path = "C:\\Users\\DELL\\Desktop\\诡秘之主\\";
        String fileOut = "C:\\Users\\DELL\\Desktop\\诡秘之主.txt";
        BufferedWriter bw = new BufferedWriter(new FileWriter(fileOut));
        // 获得指定文件对象
        File pathFile = new File(path);
        // 获得该文件夹内的所有文件
        File[] array = pathFile.listFiles();
        if (array != null) {
            for (File anArray : array) {
                if (anArray.exists()) {
                    System.out.println(anArray.getName());
                    BufferedReader br = new BufferedReader(new FileReader(anArray));
                    String line;
                    while ((line = br.readLine()) != null) {
                        bw.write(line);
                        bw.newLine();
                    }
                    br.close();
                }
            }
            bw.close();
        }
    }
}
