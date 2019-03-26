package com.rmi.demo.rmiclient.rpc;

import java.io.Serializable;

/**
 * @author: admin
 * @create: 2019/3/26
 * @update: 18:47
 * @version: V1.0
 * @detail:
 **/
public class RpcRequest implements Serializable{
    private static final long serialVersionUID = -4008944906101468556L;

    private String className;
    private String methodName;
    private Object[] parameters;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }
}
