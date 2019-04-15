package io.gjf.commons;

import io.gjf.protocol.*;
import io.gjf.serializer.JDKSerializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

import java.util.HashMap;
import java.util.Map;

/**
 * Creat by GuoJF on mac
 */
public class NettyTransport implements Transport {

    private Bootstrap bootstrap;
    private EventLoopGroup workerEventLoopGroup;

    public NettyTransport() {


        bootstrap = new Bootstrap();

        workerEventLoopGroup = new NioEventLoopGroup();
        bootstrap.group(workerEventLoopGroup);
        bootstrap.channel(NioSocketChannel.class);
    }

    @Override
    public ResultWrap transfer(HostAndPort hostAndPort,  MethodInvokeMetaWrap methodInvokeMetaWrap) throws InterruptedException {

        Map<String, ResultWrap> resultWrapMap = new HashMap<>();
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                ChannelPipeline pipeline = socketChannel.pipeline();


                pipeline.addLast(new LengthFieldBasedFrameDecoder(65535, 0, 2, 0, 2));
                pipeline.addLast(new ObjectDecoder(new JDKSerializer()));

                pipeline.addLast(new LengthFieldPrepender(2));
                pipeline.addLast(new ObjectEncoder(new JDKSerializer()));

                pipeline.addLast(new ChannelHandlerAdapter() {
                    @Override
                    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                       cause.printStackTrace();
                    }

                    @Override
                    public void channelActive(ChannelHandlerContext ctx) throws Exception {


                        ChannelFuture channelFuture = ctx.writeAndFlush(methodInvokeMetaWrap);
                        channelFuture.addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
                        channelFuture.addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
                    }

                    @Override
                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                        ResultWrap resultWrap = (ResultWrap) msg;

                        resultWrapMap.put("res", resultWrap);

                    }
                });
            }
        });


            ChannelFuture channelFuture = bootstrap.connect(hostAndPort.getHost(), hostAndPort.getPort()).sync();
            channelFuture.channel().closeFuture().sync();



        return resultWrapMap.get("res");


    }

    @Override
    public void close() {
        workerEventLoopGroup.shutdownGracefully();
    }
}
