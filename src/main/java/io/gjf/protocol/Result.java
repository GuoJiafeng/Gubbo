package io.gjf.protocol;

import java.io.Serializable;

/**
 * Create by GuoJF on 2019/4/15
 */
public class Result  implements Serializable {
    private Object returnValue;
    private RuntimeException exception;

    public Result() {
    }

    public Result(Object returnValue) {
        this.returnValue = returnValue;
    }

    public Object getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(Object returnValue) {
        this.returnValue = returnValue;
    }

    public RuntimeException getException() {
        return exception;
    }

    public void setException(RuntimeException exception) {
        this.exception = exception;
    }
}
