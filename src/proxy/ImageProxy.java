package proxy;

/**
 * @author hiYuzu
 * @version V1.0
 * @date 2019/8/30 10:14
 */
public class ImageProxy implements Image {
    private HighResolutionImage highResolutionImage;

    ImageProxy(HighResolutionImage highResolutionImage) {
        this.highResolutionImage = highResolutionImage;
    }

    @Override
    public void showImage() {
        while (!highResolutionImage.isLoad()) {
            try {
                System.out.println("Temp Image:" + highResolutionImage.getHeight());
                Thread.sleep(200);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        highResolutionImage.showImage();
    }
}
