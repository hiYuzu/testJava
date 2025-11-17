package agent.pojo;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yuzu
 * @version v1.0
 * @since 2025/11/17 11:29
 */
public class Qwen3Param {
    private String query;
    private Map<String, String> inputs;
    private ResponseMode response_mode;
    private String user;
    private String conversation_id;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Map<String, String> getInputs() {
        return inputs;
    }

    public void setInputs(Map<String, String> inputs) {
        this.inputs = inputs;
    }

    public ResponseMode getResponse_mode() {
        return response_mode;
    }

    public void setResponse_mode(ResponseMode response_mode) {
        this.response_mode = response_mode;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getConversation_id() {
        return conversation_id;
    }

    public void setConversation_id(String conversation_id) {
        this.conversation_id = conversation_id;
    }

    public Qwen3Param(String query) {
        this.query = query;
        this.inputs = new HashMap<>();
        this.response_mode = ResponseMode.blocking;
        this.user = "default";
        this.conversation_id = "";
    }

    public enum ResponseMode {
        streaming("streaming"),
        blocking("blocking");

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        ResponseMode(String name) {
            this.name = name;
        }
    }
}
