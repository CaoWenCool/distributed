package com.rmi.demo.rmiclient;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author: admin
 * @create: 2019/3/26
 * @update: 18:02
 * @version: V1.0
 * @detail:
 **/
public interface IHelloService extends Remote{

    String sayHello(String msg) throws RemoteException;
}
