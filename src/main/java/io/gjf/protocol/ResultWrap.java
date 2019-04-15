package io.gjf.protocol;

import java.io.Serializable;
import java.util.Map;

/**
 * Create by GuoJF on 2019/4/15
 */
public class ResultWrap implements Serializable {

    private Result result;

    private Map<Object, Object> attchments;


    public ResultWrap() {
    }

    public ResultWrap(Result result, Map<Object, Object> attchments) {
        this.result = result;
        this.attchments = attchments;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Map<Object, Object> getAttchments() {
        return attchments;
    }

    public void setAttchments(Map<Object, Object> attchments) {
        this.attchments = attchments;
    }
}
