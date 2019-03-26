package com.rmidemo.rmiserver;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author: admin
 * @create: 2019/3/26
 * @update: 17:54
 * @version: V1.0
 * @detail:
 **/
public class HelloServiceImpl  extends UnicastRemoteObject implements  IHelloService{

    protected HelloServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public String sayHello(String msg) throws RemoteException {
        return "Hello" + msg;
    }
}
