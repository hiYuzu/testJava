package proxy;

import java.net.URL;

/**
 * @author hiYuzu
 * @version V1.0
 * @date 2019/8/30 10:17
 */
public class ImageViewer {
    public static void main(String[] args) throws Exception {
        String image = "http://image.jpg";
        URL imageUrl = new URL(image);
        HighResolutionImage highResolutionImage = new HighResolutionImage(imageUrl);
        ImageProxy imageProxy = new ImageProxy(highResolutionImage);
        imageProxy.showImage();
    }
}
