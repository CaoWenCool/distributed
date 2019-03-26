package com.rmidemo.rmiserver.rpc;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * @author: admin
 * @create: 2019/3/26
 * @update: 18:24
 * @version: V1.0
 * @detail:
 **/
public class ProcessorHandler implements Runnable{

    private Socket socket;
    private Object service;//服务端发布发布分服务

    public ProcessorHandler(Socket socket, Object service) {
        this.socket = socket;
        this.service = service;
    }

    @Override
    public void run() {
        //处理请求
        ObjectInputStream inputStream = null;

        try {
            //获取客户端的输入流
            inputStream = new ObjectInputStream(socket.getInputStream());
            //反序列化远程传输的对象RpcRequest

            RpcRequest rpcRequest = (RpcRequest) inputStream.readObject();
            Object result=  invoke(rpcRequest);

            //通过输出流将结果输出给客户端
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(result);
            objectOutputStream.flush();
            objectOutputStream.close();
            inputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private Object invoke(RpcRequest rpcRequest)throws InvocationTargetException,IllegalAccessException,NoSuchMethodException{
        //以下均为反射操作，目的是通过反射调用服务
        Object[]  args = rpcRequest.getParameters();
        Class<?>[] types= new Class[args.length];
        for(int i=0;i<args.length;i++){
            types[i] = args[i].getClass();
        }
        Method method = service.getClass().getMethod(rpcRequest.getMethodName(),types);
        return method.invoke(service,args);
    }
}
