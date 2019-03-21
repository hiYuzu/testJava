package src;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 *
 * @author hiYuzu
 * @version V1.0
 * </p>
 * @description 下载指定页面图片
 * @date 2018/12/4 10:27
 */
public class GetPicUtil {
    /**
     * 地址
     */
    private static final String ADDRESS = "https://www.tianqiapi.com/";
    /**
     * 获取img标签正则
     */
    private static final String IMG_URL_REG = "<img.*[\\n]\\s*src=(.*?)[^>]*?>";
    /**
     * 获取src路径的正则
     */
    private static final String IMG_SRC_REG = "[a-zA-z]+://[^\\s]*";

    public void main() {
        try {
            GetPicUtil main = new GetPicUtil();
            //获得html文本内容
            String html = main.getHtml(ADDRESS);
            System.err.println(html);
            //获取图片标签
            List<String> imgUrl = main.getImageUrl(html);
            //获取图片src地址
            List<String> imgSrc = main.getImageSrc(imgUrl);
            //下载图片
            main.Download(imgSrc);

        } catch (Exception e) {
            System.out.println("发生错误");
            e.printStackTrace();
        }

    }

    /**
     * 获取HTML内容
     *
     * @param address
     * @return
     * @throws Exception
     */
    private String getHtml(String address) throws Exception {
        URL url = new URL(address);
        URLConnection connection = url.openConnection();
        InputStream in = connection.getInputStream();
        InputStreamReader isr = new InputStreamReader(in);
        BufferedReader br = new BufferedReader(isr);

        String line;
        StringBuffer sb = new StringBuffer();
        while ((line = br.readLine()) != null) {
            sb.append(line, 0, line.length());
            sb.append('\n');
        }
        br.close();
        isr.close();
        in.close();
        return sb.toString();
    }

    /**
     * 获取ImageUrl地址
     *
     * @param html
     * @return
     */
    private List<String> getImageUrl(String html) {
        Matcher matcher = Pattern.compile(IMG_URL_REG).matcher(html);
        List<String> imageUrlList = new ArrayList<String>();
        while (matcher.find()) {
            imageUrlList.add(matcher.group());
        }
        System.out.println(imageUrlList.size());
        return imageUrlList;
    }

    /**
     * 获取ImageSrc地址
     *
     * @param imageUrlList
     * @return
     */
    private List<String> getImageSrc(List<String> imageUrlList) {
        List<String> imageSrcList = new ArrayList<String>();
        for (String image : imageUrlList) {
            Matcher matcher = Pattern.compile(IMG_SRC_REG).matcher(image);
            while (matcher.find()) {
                imageSrcList.add(matcher.group().substring(0, matcher.group().length() - 1));
            }
        }
        return imageSrcList;
    }

    /**
     * 下载图片
     *
     * @param imageSrcList
     */
    private void Download(List<String> imageSrcList) {
        try {
            //开始时间
            Date beginTime = new Date();
            for (String url : imageSrcList) {
                //开始时间
                Date onceBeginTime = new Date();
                String imageName = url.substring(url.lastIndexOf("/") + 1, url.length());
                URL uri = new URL(url);
                InputStream in = uri.openStream();
                String dir = "E:\\MyIntelliJWorkspaces\\GitRepository\\testJava\\file\\" + imageName;
                FileOutputStream fo = new FileOutputStream(new File(dir));
                byte[] buf = new byte[1024];
                int length = 0;
                System.out.println("开始下载:" + url);
                while ((length = in.read(buf, 0, buf.length)) != -1) {
                    fo.write(buf, 0, length);
                }
                in.close();
                fo.close();
                System.out.println(imageName + "下载完成");
                //结束时间
                Date onceOverTime = new Date();
                double time = onceOverTime.getTime() - onceBeginTime.getTime();
                System.out.println("耗时：" + time / 1000 + "s");
            }
            Date overTime = new Date();
            double time = overTime.getTime() - beginTime.getTime();
            System.out.println("总耗时：" + time / 1000 + "s");
        } catch (Exception e) {
            System.out.println("下载失败");
            e.printStackTrace();
        }
    }
}