package com.rmidemo.rmiserver.rpc;

/**
 * @author: admin
 * @create: 2019/3/26
 * @update: 18:45
 * @version: V1.0
 * @detail:
 **/
public class IRPCHelloImpl implements IRPCHello{
    @Override
    public String sayHello(String msg) {
        return "hello"+msg;
    }
}
