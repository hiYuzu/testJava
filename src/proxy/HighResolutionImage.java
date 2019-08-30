package proxy;

import java.net.URL;

/**
 * @author hiYuzu
 * @version V1.0
 * @date 2019/8/30 10:10
 */
public class HighResolutionImage implements Image {
    private URL imageUrl;
    private long startTime;
    private int height;
    private int width;

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public HighResolutionImage(URL imageUrl) {
        this.imageUrl = imageUrl;
        this.startTime = System.currentTimeMillis();
        this.height = 600;
        this.width = 600;
    }

    public boolean isLoad() {
        // 模拟图片加载，延迟 3s 加载完成
        long endTime = System.currentTimeMillis();
        return endTime - startTime > 3000;
    }

    @Override
    public void showImage() {
        System.out.println("Real Image:" + imageUrl);
    }
}
