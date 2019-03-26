package com.rmi.demo.rmiclient;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * @author: admin
 * @create: 2019/3/26
 * @update: 18:02
 * @version: V1.0
 * @detail:
 **/
public class ClientDemo {

    public static void main(String[] args)throws RemoteException, NotBoundException, MalformedURLException {
        IHelloService helloService=
                (IHelloService)Naming.lookup("rmi://127.0.0.1:1099/Hello");
        // HelloServiceImpl实例(HelloServiceImpl_stub)
        // RegistryImpl_stub
        System.out.println(helloService.sayHello("Mic"));
    }
}
