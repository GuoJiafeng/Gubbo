package io.gjf.protocol;

import java.io.Serializable;
import java.util.Map;

/**
 * Create by GuoJF on 2019/4/15
 * 请求报文 附件
 */
public class MethodInvokeMetaWrap implements Serializable {

    private MethodInvokeMeta invokeMeta;

    private Map<Object,Object> attchments;

    public MethodInvokeMetaWrap(MethodInvokeMeta invokeMeta) {
        this.invokeMeta = invokeMeta;
    }

    public MethodInvokeMeta getInvokeMeta() {
        return invokeMeta;
    }

    public void setInvokeMeta(MethodInvokeMeta invokeMeta) {
        this.invokeMeta = invokeMeta;
    }

    public Map<Object, Object> getAttchments() {
        return attchments;
    }

    public void setAttchments(Map<Object, Object> attchments) {
        this.attchments = attchments;
    }
}
