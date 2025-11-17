package agent;

import agent.pojo.Qwen3Param;
import agent.pojo.ExplainSuggestResult;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

/**
 * @author yuzu
 * @version v1.0
 * @since 2025/11/17 11:24
 */
public class ExplainSuggestService {
    private static final String URL = "http://localhost/v1/chat-messages";
    private static final String API_KEY_START = "Bearer ";
    private static final String API_KEY = "app-sbIcigLJ8YDO9RUDbVNzAFQh";

    public static void main(String[] args) {
        ExplainSuggestService explainSuggestService = new ExplainSuggestService();
        String question = "这是对于呼吸道传染病数据的分析，date代表日期，target代表发病人数，原始数据是" +
                "\"date,target,cov_pm25,cov_temp,cov_mobility\\n2025-01-01,18,54.2,8.3,0.34\\n2025-02-01,20,50.7,6.9,0.33\\n2025-03-01,17,52.4,11.0,0.31\\n2025-04-01,19,55.6,9.4,0.32\\n2025-05-01,22,56.1,8.9,0.34\\n2025-06-01,18,53.9,7.6,0.33\\n2025-07-01,21,51.5,8.5,0.30\\n2025-08-01,19,49.9,9.1,0.29\\n2025-09-01,20,50.2,10.0,0.31\\n2025-10-01,23,48.7,9.7,0.26\\n2025-11-01,21,46.9,8.8,0.27\\n2025-12-01,24,47.5,10.2,0.28\\n2026-01-01,22,49.1,9.4,0.32\\n2026-02-01,20,51.8,8.1,0.31\"" +
                "；预测数据预测发病人数为" +
                "[\n" +
                "\t\t{\n" +
                "\t\t\t\"forecast\": 20,\n" +
                "\t\t\t\"time\": \"2026-02-02\"\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"forecast\": 20,\n" +
                "\t\t\t\"time\": \"2026-02-03\"\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"forecast\": 19,\n" +
                "\t\t\t\"time\": \"2026-02-04\"\n" +
                "\t\t}\n" +
                "\t]" +
                "。";
        explainSuggestService.getResult(question);
    }

    public ExplainSuggestResult getResult(String question) {
        String Authorization = API_KEY.startsWith(API_KEY_START) ? API_KEY : API_KEY_START + API_KEY;
        Qwen3Param qwen3Param = new Qwen3Param(question);
        HttpRequest request = HttpUtil.createPost(URL)
                .header("Authorization", Authorization)
                .header("Content-Type", "application/json")
                .body(JSONUtil.toJsonStr(qwen3Param));
        try (HttpResponse response = request.execute()) {
            if (response.isOk()) {
                JSONObject bodyJson = JSONUtil.parseObj(response.body());
                ExplainSuggestResult explainSuggestResult = JSONUtil.toBean(bodyJson.getJSONObject("answer"), ExplainSuggestResult.class);
                System.out.println(JSONUtil.toJsonPrettyStr(explainSuggestResult));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
