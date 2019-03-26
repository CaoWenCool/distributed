package com.rmidemo.rmiserver.rpc;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: admin
 * @create: 2019/3/26
 * @update: 18:33
 * @version: V1.0
 * @detail: 用于发布一个远程服务
 **/
public class RpcServer {

    private  static final ExecutorService executorService = Executors.newCachedThreadPool();

    public void publisher(final Object service,int port){
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);

            //循环监听
            while(true){
                Socket socket = serverSocket.accept();//监听服务
                //通过线程池去处理请求
                executorService.execute(new ProcessorHandler(socket,service));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(serverSocket != null){
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
