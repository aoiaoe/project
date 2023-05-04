package com.geekbang.in_action.rate_limit.v1.rules.rule_match;

import com.geekbang.in_action.rate_limit.v1.rules.ApiLimit;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jzm
 * @date 2023/4/27 : 10:23
 */
public class TrieLimitNode {

    private static final String ROOT = "/";

    private String path;

    private Map<String, TrieLimitNode> nextPath;

    private ApiLimit limit;

    public TrieLimitNode(String path, Map<String, TrieLimitNode> nextPath, ApiLimit limit) {
        this.path = path;
        this.nextPath = nextPath;
        this.limit = limit;
    }

    public ApiLimit match(String api) {
        if(path.equals(api)){
            return limit;
        }
        if (nextPath == null) {
            return null;
        }
        int index = api.indexOf("/");

        if(index == 0){
            api = api.substring(1);
            index = api.indexOf("/");
        }
        String trieWord = api;
        if (index != -1) {
            trieWord = trieWord.substring(0, index);
        }
        TrieLimitNode node = nextPath.get(trieWord);
        if(node == null){
            return null;
        }
        return node.match(api.substring(index + 1));
    }

    public void addLimit(String api, ApiLimit limit) {
        if (api == null) {
            return;
        }
        if (nextPath == null) {
            nextPath = new HashMap<>();
        }
        int index = api.indexOf("/");
        if(index == 0){
            api = api.substring(1);
            index = api.indexOf("/");
        }
        if (api.indexOf("/") == -1) {
            if (nextPath.containsKey(api)) {
                nextPath.get(api).setLimit(limit);
                return;
            }
            nextPath.put(api, new TrieLimitNode(api, null, limit));
        } else {
            String trieWord = api.substring(0, index);
            if (!nextPath.containsKey(trieWord)) {
                nextPath.put(trieWord, new TrieLimitNode(trieWord, new HashMap<>(), null));
            }
            nextPath.get(trieWord).addLimit(api.substring(index + 1), limit);
        }
    }

    public void setLimit(ApiLimit limit) {
        this.limit = limit;
    }

    public ApiLimit getLimit() {
        return limit;
    }
}
