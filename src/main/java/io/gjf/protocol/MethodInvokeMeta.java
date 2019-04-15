package io.gjf.protocol;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Create by GuoJF on 2019/4/15
 *
 * 请求报文
 */
public class MethodInvokeMeta  implements Serializable {

    private Class targetInterface;
    private String methodName;
    private Class<?> paramterTypes;
    private Object[] args;


    public MethodInvokeMeta() {
    }

    public MethodInvokeMeta(Class targetInterface, String methodName, Class<?> paramterTypes, Object[] args) {
        this.targetInterface = targetInterface;
        this.methodName = methodName;
        this.paramterTypes = paramterTypes;
        this.args = args;
    }


    public Class getTargetInterface() {
        return targetInterface;
    }

    public void setTargetInterface(Class targetInterface) {
        this.targetInterface = targetInterface;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?> getParamterTypes() {
        return paramterTypes;
    }

    public void setParamterTypes(Class<?> paramterTypes) {
        this.paramterTypes = paramterTypes;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    @Override
    public String toString() {
        return "MethodInvokeMeta{" +
                "targetInterface=" + targetInterface +
                ", methodName='" + methodName + '\'' +
                ", paramterTypes=" + paramterTypes +
                ", args=" + Arrays.toString(args) +
                '}';
    }
}
