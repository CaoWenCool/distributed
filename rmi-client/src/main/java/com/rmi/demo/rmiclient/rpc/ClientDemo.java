package com.rmi.demo.rmiclient.rpc;

/**
 * @author: admin
 * @create: 2019/3/26
 * @update: 18:46
 * @version: V1.0
 * @detail:
 **/
public class ClientDemo {
    public static void main(String[] args) {
        RpcClientProxy rpcClientProxy = new RpcClientProxy();

        IRPCHello hello = rpcClientProxy.clientProxy(IRPCHello.class,"localhost",7777);
        System.out.println(hello.sayHello("cc"));

    }
}
