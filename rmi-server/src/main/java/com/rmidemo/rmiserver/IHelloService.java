package com.rmidemo.rmiserver;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author: admin
 * @create: 2019/3/26
 * @update: 17:55
 * @version: V1.0
 * @detail:
 **/
public interface IHelloService extends Remote{

    String sayHello(String msg) throws RemoteException;
}
