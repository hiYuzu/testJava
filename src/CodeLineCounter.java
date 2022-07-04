import cn.hutool.core.util.ObjectUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * @author hiYuzu
 * @version V1.0
 * @date 2022/4/7 14:42
 */
public class CodeLineCounter {
    private static int totalLine;
    /**
     * 所需计算的源码文件类型
     */
    private static final String[] FILE_TO_BE_COUNTED = {"java", "jsp", "css", "html", "htm", "xml"};

    private static boolean isFileToBeCounted(String type) {
        //所需计算的源码文件类型个数
        int fileToBeCountedN = 6;
        for (int i = 0; i < fileToBeCountedN; i++) {
            if (type.equals(FILE_TO_BE_COUNTED[i])) {
                return true;
            }
        }
        return false;
    }

    private static String getType(String filename) {
        byte[] b = filename.getBytes();
        byte[] type = new byte[10];
        String rts = null;
        int i = 0, p = 0, n = filename.length();
        for (i = 0; i < n; i++) {
            if (b[i] == '.') {
                p = i;
                break;
            }
        }
        i = p + 1;
        p = 0;
        for (; i < n && p < 10; i++) {
            type[p++] = b[i];
        }
        rts = new String(type);
        return rts.substring(0, p);
    }

    private static void countLine(String path) {
        File file = new File(path);
        File[] lists = file.listFiles();
        if (ObjectUtil.isNull(lists)) {
            return;
        }
        for (File list : lists) {
            if (list.isFile()) {
                String filename = list.getName();
                boolean isFileToBeCounted = isFileToBeCounted(getType(filename));
                if (isFileToBeCounted) {
                    try {
                        int lines = 0;
                        FileReader read = new FileReader(path + filename);
                        BufferedReader br = new BufferedReader(read);
                        while (br.readLine() != null) {
                            lines++;
                        }
                        System.out.println("文件：" + path + filename + "共:" + lines + "行代码;");
                        totalLine += lines;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                String paths = path;
                paths = paths + list.getName() + "\\";
                System.out.println("进入目录：" + paths + ";");
                countLine(paths);
            }
        }
    }

    public static void main(String[] args) {
        totalLine = 0;
        // 项目的绝对地址
        String path = "E:\\JavaProject\\QMJK_AGWS_ORACLE\\src\\main\\webapp\\WEB-INF\\views\\";
        countLine(path);
        System.out.println("整个项目共:" + totalLine + "行代码;");
    }
}