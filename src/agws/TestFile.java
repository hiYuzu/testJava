package agws;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.KeyUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;
import cn.hutool.http.HttpUtil;
import org.bouncycastle.crypto.engines.SM2Engine;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>接口请求测试</p>
 * <p>JDK版本：1.8</p>
 *
 * @author sinosoft
 * @version V1.0
 * @date 2023/2/19 14:51
 */
public class TestFile {
    private static final String SYS_PROTOCOL = "http";
    private static final String SYS_IP = "127.0.0.1";
    private static final String SYS_PORT = "80";
    private static final String SYS_METHOD = "/openApi/baseInfo/awbBaseInfoFile";
    private static final String IIG_HEADER = "IIG-AUTH";
    private static final String IIG_AUTH = "jlf5ydoq-u7dh-olrp-n2mk-a8lrc8q3nfkw";
    private static final String PUBLIC_KEY_STR = "3059301306072a8648ce3d020106082a811ccf5501822d03420004640bef3e59e1431c27fd36a6fbfdbf8cdecaf5b16b16b747d57f3f312ad71ad678ab3487dcb928488b092e48631e9c7d19f0a75875df13d35bdbd6e33c681c06";

    /**
     * 测试入口
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        File file = FileUtil.file("E:\\1.pdf");
        byte[] buffer = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream baOs = new ByteArrayOutputStream(fis.available());
            byte[] bytes = new byte[fis.available()];
            int temp;
            while ((temp = fis.read(bytes)) != -1) {
                baOs.write(bytes, 0, temp);
            }
            fis.close();
            baOs.close();
            buffer = baOs.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // JSON字符串格式
        String bufferStr = Base64.getEncoder().encodeToString(buffer);
        // SM2加密
        byte [] publicKey = HexUtil.decodeHex(PUBLIC_KEY_STR);
        SM2 sm2 = SmUtil.sm2();
        sm2.setMode(SM2Engine.Mode.C1C2C3);
        sm2.setPublicKey(KeyUtil.generatePublicKey("SM2", publicKey));
        String result = sm2.encryptBase64(bufferStr, StandardCharsets.UTF_8, KeyType.PublicKey);
        System.out.println("密文：" + result);
        // 传参Map
        Map<String, Object> paramMap = new HashMap<>(5);
        // 机构编码，这里以辽宁省大连市为例
        paramMap.put("orgCode", "21020000000");
        paramMap.put("businessId", "40284f8185766622018576914b410000");
        paramMap.put("fileName", "测试.pdf");
        paramMap.put("filedNameVal", "1,1");
        // 上传文档类别
        //01 法规
        //02 规章
        //03 一般规范性文件
        //04 公文
        paramMap.put("documentType", "03");
        // 发文/发布部门
        paramMap.put("releaseDepartment", "测试发布部门001");
        // 发布/发文日期
        paramMap.put("releaseTime", "2023-03-01");
        // 生效日期
        paramMap.put("effectTime", "2023-03-01");
        // 文档修订日期（非必填）
//        paramMap.put("revisionTime", "2023-03-01");
        // 文档状态
        paramMap.put("docStatus", "1");
        // 加密后的数据
        paramMap.put("encStr", result);
        final int timeout = 20000;
        String responseBody = HttpUtil.createPost(SYS_PROTOCOL + "://" + SYS_IP + ":" + SYS_PORT + SYS_METHOD)
                .header(IIG_HEADER, IIG_AUTH)
                .form(paramMap)
                .timeout(timeout)
                .execute()
                .body();
        System.out.println(responseBody);
    }
}

