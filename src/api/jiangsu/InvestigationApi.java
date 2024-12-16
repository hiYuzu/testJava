package api.jiangsu;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yuzu
 * @version v1.0
 * @since 2024/9/22 10:35
 */
public class InvestigationApi {
    private static final String URL_1 = "http://10.41.10.230/api/gsurvey/spt/query_task";
    private static final String URL_2 = "http://10.41.10.230/api/gsurvey/spt/bind_task";
    private static final String CONTENT_TYPE = "application/json";
    private static final int TIMEOUT = 5000;

    public static void main(String[] args) throws Exception {
        System.out.println("测试流调任务查询...");
        queryTaskTest();
        System.out.println("测试流调任务绑定...");
        bindTaskTest();

    }

    /**
     * 查询流调任务
     */
    private static void queryTaskTest() {
        HttpResponse response = HttpUtil.createPost(URL_1)
                .header("EPID", "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMII")
                .header("token", "eyJhbGciOiJIUzUxMiJ9.eyJsb2dpbl91c2VyX2tleSI6IjZiMTY2OGQwLTRkYjMtNDljMS05YzUyLWJiNjY3MzRmNjdjMCJ9.Tbrj3182Yvk6bSVgO38esL-KCZdK0V_QjzBDEDFLGq2YH9o5HNhMb4O7bFPhZVh1ZLomw4zj5YveO1gzcBvebA")
                .contentType(CONTENT_TYPE)
                .body("{}")
                .timeout(TIMEOUT)
                .execute();
        System.out.println("响应状态码：" + response.getStatus());
        System.out.println("结果：" + response.isOk());
        String responseBody = response.body();
        System.out.println("返回结果：\n" + responseBody);
    }

    /**
     * 绑定流调任务
     */
    private static void bindTaskTest() {
        Map<String, String> param = new HashMap<>(3);
        param.put("cardId", "xxxxxxxxxxxxx");
        param.put("taskId", "1033822940975730688");
        String body = JSON.toJSONString(param);
        System.out.println("请求参数：\n" + body);
        HttpResponse response = HttpUtil.createPost(URL_2)
                .header("EPID", "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMII")
                .header("token", "eyJhbGciOiJIUzUxMiJ9.eyJsb2dpbl91c2VyX2tleSI6IjZiMTY2OGQwLTRkYjMtNDljMS05YzUyLWJiNjY3MzRmNjdjMCJ9.Tbrj3182Yvk6bSVgO38esL-KCZdK0V_QjzBDEDFLGq2YH9o5HNhMb4O7bFPhZVh1ZLomw4zj5YveO1gzcBvebA")
                .contentType(CONTENT_TYPE)
                .body(body)
                .timeout(TIMEOUT)
                .execute();
        System.out.println("响应状态码：" + response.getStatus());
        System.out.println("结果：" + response.isOk());
        String responseBody = response.body();
        System.out.println("返回结果：\n" + responseBody);
    }
}
