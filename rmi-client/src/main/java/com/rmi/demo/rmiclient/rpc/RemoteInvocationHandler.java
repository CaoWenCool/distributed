package com.rmi.demo.rmiclient.rpc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author: admin
 * @create: 2019/3/26
 * @update: 18:46
 * @version: V1.0
 * @detail:
 **/
public class RemoteInvocationHandler implements InvocationHandler{
    private String host;
    private int port;


    public RemoteInvocationHandler(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //组装请求
        RpcRequest request = new RpcRequest();
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setParameters(args);

        //通过tcp传输协议进行传输
        TCPTransport tcpTransport = new TCPTransport(this.host,this.port);

        //发送请求
        return tcpTransport.send(request);
    }
}
