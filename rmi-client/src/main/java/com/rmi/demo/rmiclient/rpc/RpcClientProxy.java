package com.rmi.demo.rmiclient.rpc;

import java.lang.reflect.Proxy;

/**
 * @author: admin
 * @create: 2019/3/26
 * @update: 18:57
 * @version: V1.0
 * @detail:
 **/
public class RpcClientProxy {

    public<T> T clientProxy(final Class<T> interfaceCls,final String host,final int port){

        //使用了动态代理
        return (T) Proxy.newProxyInstance(interfaceCls.getClassLoader(),new Class[]{interfaceCls},new RemoteInvocationHandler(host,port));
    }
}
