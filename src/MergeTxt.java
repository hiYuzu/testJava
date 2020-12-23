import java.io.*;

/**
 * 合并小说章节
 *
 * @author hiYuzu
 * @version V1.0
 * @date 2020/7/28 13:34
 */
public class MergeTxt {
    private static boolean isDone = true;
    public static void main(String[] args) throws IOException {
        String path = "C:\\Users\\DELL\\Desktop\\wxkb\\";
        String fileOut = "C:\\Users\\DELL\\Desktop\\wxkb.txt";
        BufferedWriter bw = new BufferedWriter(new FileWriter(fileOut));
        File pathFile = new File(path);
        File[] array = pathFile.listFiles();
        if (array != null) {
            int[] chapterCount = new int[9999];
            int i = 0;
            for (File anArray : array) {
                if (anArray.exists()) {
                    String chapterName = anArray.getName();
                    if (chapterName.contains("第") && chapterName.contains("章")) {
                        String chapterNum = chapterName.substring(chapterName.indexOf("第") + 1, chapterName.indexOf("章"));
                        chapterCount[i] = Integer.parseInt(chapterNum);
                        if (i > 0) {
                            if (chapterCount[i] - chapterCount[i - 1] > 1) {
                                System.out.println("章节缺失：第" + chapterCount[i - 1] + "章 - 第" + chapterCount[i] + "章");
                                isDone = false;
                            }
                        }
                        i++;
                    }
                    if (isDone) {
                        BufferedReader br = new BufferedReader(new FileReader(anArray));
                        String line;
                        while ((line = br.readLine()) != null) {
                            bw.write(line);
                            bw.newLine();
                        }
                        br.close();
                    }
                }
            }
            bw.close();
            System.out.println(isDone ? "合并成功！" : "章节遗失，合并失败");
            if (!isDone) {
                File file = new File(fileOut);
                if (!file.delete()) {
                    System.out.println("删除失败");
                }
            }
        } else {
            System.out.println("未检测到文件");
        }
    }
}
