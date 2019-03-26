package com.rmidemo.rmiserver.rpc;

/**
 * @author: admin
 * @create: 2019/3/26
 * @update: 18:44
 * @version: V1.0
 * @detail:
 **/
public class ServerDemo {

    public static void main(String[] args) {
        IRPCHello irpcHello = new IRPCHelloImpl();
        RpcServer rpcServer = new RpcServer();
        rpcServer.publisher(irpcHello,7777);
    }
}
