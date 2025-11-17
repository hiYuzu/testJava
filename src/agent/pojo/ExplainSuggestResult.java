package agent.pojo;

import java.util.List;

/**
 * @author yuzu
 * @version v1.0
 * @since 2025/11/17 11:25
 */
public class ExplainSuggestResult {
    private List<String> explain;
    private List<String> suggest;

    public List<String> getExplain() {
        return explain;
    }

    public void setExplain(List<String> explain) {
        this.explain = explain;
    }

    public List<String> getSuggest() {
        return suggest;
    }

    public void setSuggest(List<String> suggest) {
        this.suggest = suggest;
    }
}
