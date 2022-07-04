import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author hiYuzu
 * @version V1.0
 * @date 2022/3/10 16:07
 */
public class Main {
    public static void main(String[] args) {
        Map<String, Object> checks = new HashMap<>(7);
//        checks.putAll(JSONObject.parseObject("{\"name\":[\"tom\",\"jerry\"]}", Map.class));
        try {
            org.json.JSONObject jsonObject = new org.json.JSONObject("{\"name\":[\"tom\",\"jerry\"]}");
            Iterator it = jsonObject.keys();
            while (it.hasNext()) {
                String key = (String) it.next();
                String value = jsonObject.getString(key);
                checks.put(key, value);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            System.out.println((String) checks.get("name"));
            org.json.JSONArray roles = new org.json.JSONArray((String) checks.get("name"));
//            org.json.JSONArray roles = new org.json.JSONArray("[name,name2]");
//        	JSONArray roles=(JSONArray) checks.get("name");
            for(int i=0;i<roles.length();i++){
                System.out.println(roles.get(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
