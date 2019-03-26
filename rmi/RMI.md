## 什么是RPC
RPC（Remote Procedure Call,远程过程调用），一般用来实现部署在不同机器上的系统之间的方法调用，使得程序能够像访问本地系统资源
一样，通过网络传输去访问远端系统资源，对于客户端来说，传输层是使用什么协议，序列化、反序列化都是透明的。 

## RMI
RMI 全称是 Remote method invocation 远程方法调用，一种用于远程过程调用的应用程序编程接口，是纯java的网络分布式应用系统的核心
解决方案之一。
RMI 目前使用java 远程消息交换协议JRMP（JAVA Remote Messageing Protocol）进行通信，由于JRMP是专为JAVA对象制定的，是分布式应用
系统的百分之百纯JAVA解决方案，用Java RMI开发的应用系统可以部署在任何支持JRE的平台上，缺点是，由于JRMP是专门为Java对象指定的，因此
RMI对于非JAVA语言开发的应用系统的支持不足，不能与非JAVA语言书写的对象进行通信。   

## JAVA RMI 代码实践
详见代码  rmi-server 和 rmi-client
远程对象必须实现 UnicastRemoteObject,这样才能保证客户端访问获得远程对象时，该远程对象会把自身的一个拷贝一Socket形式传输给客户端
，客户端获得的拷贝称为stub，而服务器端本身已经存在的远程对象称为 “skeleton”，此时客户端的stub时客户端的一个代理，还用与服务器端
进行通信，而skeleton时服务端的一个代理，用于接收客户端的请求之后调用远程方法来响应客户端的请求。  


## JAVA RMI 源码分析

远程对象发布
    
远程应用层 


### 发布远程对象
发布远程对象，看到上面的类图可以知道，这个地方会发布两个远程对象，一个时RegistryImpl、另一个是我们自己写的RMI实现类对象。  
从HelloServiceImpl的构造函数看起，调用了父类UnicastRemoteObject的构造方法，追溯到UnicastRemoteObject的私有方法exportObject().
这里做了一个欧安段，判断服务的实现是不是UnicastRemoteObject的子类，如果是，则直接赋值气ref(RemoteRef)对象为传入的UnicastServerRef
对象，反之则调用UnicastServerRef的exportObject（）方法。
    
    IHelloService helloService = new HelloServiceImpl();
    
因为HelloServiceImpl 继承了UnicastRemoteObject，所以在服务启动的时候，会通过UnicastRemoteObject，所以在服务启动的时候，会通过
UnicastRemoteObject的构造方法把对象进行发布

    public class HelloServiceImpl extends UnicastRemoteObject{
        protected HelloServiceImpl() throws RemoteException{
            super();
        }
    }
    public static Remote exportObject(Remote obj,int port)throws RemoteException{
           //创建UnicastServerREF对象，对象内有引用了LiveRef(tcp通信)
           return exportObject(obj,new UnicastServerRef(port));
    }
    
    public Remote exportObject(Remote var1,Object var2,boolean var3)throws RemoteException{
        Class var4 = var1.getClass();
        
        Remote var5;
        try{
        //创建远程代理类，该代理实对OperationImpl对象的代理，getClientRef提供了TCP连接
            var5 = Util.createProxy(var4,this.getClientRef(),this.forceStubUse)
        }catch(IllegalArgumentException var7){
            throw new ExportException("remote object implements illegal remote interface",var7);
        }
        
        if(var5 instanceof RemoteStub){
            this.setSkeleton(var1);
        }
        
        //包装实际对象，并将其暴露在TCP端口上，等待客户端调用
        Target var6 = new Target(var1,dispatcher:this,var5,this.ref.getObjID(),var3);
        this.ref.exportObject(var6);
        this.hashToMethod_Map = (Map)hashToMethod_Maps.get(var4);
        return var5;
    }

服务端启动Registry服务
    
    LocateRegistry.createRegistry(1099);

从上面这段代码入手，可是往下看，可以发现服务端创建了一个RegistryImpl对象，这里做了一个判断，如果服务端指定的端口是1099并且系统
开启了安全管理器，那么就可以在限定的权限集内绕过系统的安全校验。这里纯粹是为了提高效率，真正的逻辑在this.setup(new UnicastServerRef())
里面

    public RegistryImpl(final int var1)throws RemoteException{
        if(var1 == 1099 && System.getSecurityManager() 1= null){
            try{
                AccessController.doPrivileged(run() ->{
                    LiveRef var1x = new LiveRef(RegistryImpl.id,var1);
                    RegistryImpl.this.setup(new UnicastServerRef(var1x,(var0)->{
                        return RegistryImpl.regisstryFilter(var0);
                    }))
                    return null;
                },(AccessControlContext)null,new Permission[]{new SocketPermission()})
            }catch(PrivilegedActionException var3){
                throw (RemoteException)var3.getException();
            }
        }else{
            LiveRef var2 = new LiveRef(id,var1);
            this.setup(new UnicastServerRef(var2,RegistryImpl::registryFilter));
        }
    }
    
 
 
...................

一定要说明，在RMI Client实施正式的RMI调用前，它必须通过LocateRegistry 或者Naming方式注册到RMI注册表寻找要调用的RMI注册信息
。找到RMI事务注册信息后，Client会从RMI注册表获取这个RMI Remote Service的Stub信息，这个过程成功后，RMI Client才能开始正式的
调用过程。  
另外要说明的是RMI Client正式调用过程，也不是由RMI Client直接访问Remote Service,而是由客户端获取的Stub作为RMI Client的代理访问
Remote Service的代理Skeleton,如上图所示的顺序，也就是真实的请求调用时在Stub-Skeleton之间进行的。
Registry 并不参与具体的Stub-Skeleton的 调用过程，只负责记录那个服务名使用哪一个Stub,并在Remote Client询问它时将这个Stub拿给
Client.

