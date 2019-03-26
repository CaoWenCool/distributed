package com.rmidemo.rmiserver;


import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class server {

	public static void main(String[] args) {
		try {
			IHelloService helloService=new HelloServiceImpl();//已经发布了一个远程对象

			LocateRegistry.createRegistry(1099);

			Naming.rebind("rmi://127.0.0.1:1099/Hello",helloService); //注册中心 key - value
			System.out.println("服务启动成功");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

}
