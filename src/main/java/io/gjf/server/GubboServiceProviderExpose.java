package io.gjf.server;

import io.gjf.commons.HostAndPort;
import io.gjf.protocol.*;
import io.gjf.resistry.GubboRegisrty;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.rmi.registry.Registry;
import java.util.Map;

/**
 * Create by GuoJF on 2019/4/15
 * 服务端 接口暴露
 */
public class GubboServiceProviderExpose {

    private Map<Class, Object> beanFactory;


    //netty 相关服务端依赖


    ServerBootstrap sbt;
    EventLoopGroup boss;
    EventLoopGroup worker;
    Integer port;

    //持有Registry对戏aNG

    private GubboRegisrty regisrty;


    private GubboServiceProviderExpose(GubboRegisrty regisrty, Integer port) {
        this.regisrty = regisrty;


        sbt = new ServerBootstrap();

        boss = new NioEventLoopGroup();
        worker = new NioEventLoopGroup();


        sbt.group(boss);
        sbt.group(worker);

        sbt.channel(NioServerSocketChannel.class);


    }


    public void start() throws Exception {

        sbt.childHandler(new ChannelInitializer<SocketChannel>() {


            protected void initChannel(SocketChannel ch) throws Exception {


                ChannelPipeline pipeline = ch.pipeline();


                pipeline.addLast(new LengthFieldBasedFrameDecoder(65535, 0, 2, 0, 2));
                pipeline.addLast(new ObjectDecoder(null));

                pipeline.addLast(new LengthFieldPrepender(2));
                pipeline.addLast(new ObjectEncoder(null));
                pipeline.addLast(new ChannelHandlerAdapter() {
                    @Override
                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

                        MethodInvokeMetaWrap methodInvokeMetaWrap = (MethodInvokeMetaWrap) msg;

                        Map<Object, Object> attchments = methodInvokeMetaWrap.getAttchments();

                        MethodInvokeMeta methodInvokeMeta = methodInvokeMetaWrap.getInvokeMeta();

                        Object obj = beanFactory.get(methodInvokeMeta.getTargetInterface());


                        Method method = obj.getClass().getMethod(methodInvokeMeta.getMethodName(), methodInvokeMeta.getParamterTypes(), methodInvokeMeta.getParamterTypes());


                        Result result = new Result();

                        try {
                            Object returnValue = method.invoke(obj, methodInvokeMeta.getArgs());
                            result.setReturnValue(returnValue);

                        } catch (Exception e) {
                            result.setException(new RuntimeException(e.getCause()));
                        }

                        ResultWrap resultWrap = new ResultWrap();
                        resultWrap.setResult(result);
                        //resultWrap.setAttchments(null);


                        ChannelFuture channelFuture = ctx.writeAndFlush(resultWrap);
                        channelFuture.addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
                        channelFuture.addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
                        channelFuture.addListener(ChannelFutureListener.CLOSE);


                    }

                    @Override
                    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                        super.exceptionCaught(ctx, cause);
                    }
                });


            }
        });

        new Thread() {
            @Override
            public void run() {

                try {
                    //次方法会阻塞 使用线程的方式开启多个
                    ChannelFuture channelFuture = sbt.bind(port).sync();
                    channelFuture.channel().closeFuture().sync();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

        //创建本地的HostAndPort

        HostAndPort hostAndPort = new HostAndPort(InetAddress.getLocalHost().getHostAddress(), port);


        //注册服务

        for (Class targetInterface : beanFactory.keySet()) {
            regisrty.regisrtyService(targetInterface, hostAndPort);
        }


    }

    public void close() {
        boss.shutdownGracefully();
        worker.shutdownGracefully();
        regisrty.close();

    }


}
