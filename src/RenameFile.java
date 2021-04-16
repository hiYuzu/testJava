import java.io.File;

/**
 * @author hiYuzu
 * @date 2020/11/29 17:27
 */
public class RenameFile {
    public static void main(String[] args) {
        String path = "D:\\Pomelo\\Documents";
        File root = new File(path);
        try {
            rename(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void rename(File dir) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    rename(file);
                } else {
                    String rootPath = file.getParent();
                    String oldName = file.getName();
                    String newName = oldName.replace(" - 副本", "");
                    if (!file.renameTo(new File(rootPath + File.separator + newName))) {
                        System.out.println("修改失败：" + file.getAbsolutePath());
                    }
                }
            }
        }
    }
}
