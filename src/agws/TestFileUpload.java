package agws;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpUtil;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.AesKeyStrength;
import net.lingala.zip4j.model.enums.EncryptionMethod;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>填报页附加上传</p>
 * <p>JDK版本：1.8</p>
 *
 * @author sinosoft
 * @version V1.0
 * @date 2023/3/2 14:51
 */
public class TestFileUpload {
    private static final String SYS_PROTOCOL = "http";
    private static final String SYS_IP = "127.0.0.1";
    private static final String SYS_PORT = "80";
    private static final String SYS_METHOD = "/openApi/file/uploadDoc";
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
        File target = FileUtil.file("E:\\1.zip");
        ZipFile zipFile = new ZipFile(target);
        ZipParameters parameters = new ZipParameters();
        parameters.setEncryptFiles(true);
        parameters.setEncryptionMethod(EncryptionMethod.AES);
        parameters.setAesKeyStrength(AesKeyStrength.KEY_STRENGTH_256);
        zipFile.setPassword(IIG_AUTH.toCharArray());
        zipFile.addFile(file, parameters);
        // 传参Map
        Map<String, Object> paramMap = new HashMap<>(15);
        // 机构编码，这里以辽宁省大连市为例
        paramMap.put("orgCode", "21020000000");
        // 业务主键
        paramMap.put("businessId", "40284f8185766622018576914b410000");
        // 文件名（目前仅允许pdf、ofd文件）
        paramMap.put("fileName", "机构编制人数.pdf");
        // 模块名，枚举类值与系统模块名对应
        paramMap.put("moduleName", FileModuleEnum.AWB_BASE_INFO.getFieldName());
        // 字段名，枚举类值与系统填报页指标对应
        paramMap.put("fieldName", FileFieldEnum.ESTABLISHMENTS.getFieldName());
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
        // 加密压缩后的文件
        paramMap.put("zipFile", zipFile);
        final int timeout = 20000;
        String responseBody = HttpUtil.createPost(SYS_PROTOCOL + "://" + SYS_IP + ":" + SYS_PORT + SYS_METHOD)
                .header(IIG_HEADER, IIG_AUTH)
                .form(paramMap)
                .timeout(timeout)
                .execute()
                .body();
        System.out.println(responseBody);
    }

    public enum FileFieldEnum {
        /**
         * 爱卫办基本信息
         */
        ESTABLISHMENTS("机构编制人数"),
        WORK_CONTENT("具体承办工作内容"),
        OTHER1("爱卫相关工作规范管理文件1"),
        OTHER2("爱卫相关工作规范管理文件2"),
        OTHER3("爱卫相关工作规范管理文件3"),
        OTHER4("爱卫相关工作规范管理文件4"),
        OTHER5("爱卫相关工作规范管理文件5"),
        /**
         * 卫生城市评估
         */
        COUNTRY_COUNT("国家卫生县"),
        TOBACCO("全面控烟法律法规规定");

        private String fieldName;

        FileFieldEnum(String fieldName) {
            this.fieldName = fieldName;
        }

        public String getFieldName() {
            return fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }
    }
    public enum FileModuleEnum {
        /**
         * 爱卫办基本信息模块名
         */
        AWB_BASE_INFO("爱卫办基本信息"),
        /**
         * 卫生城市评估模块名
         */
        SAN_CITY("卫生城市评估");

        private String fieldName;

        FileModuleEnum(String fieldName) {
            this.fieldName = fieldName;
        }

        public String getFieldName() {
            return fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }
    }

}

