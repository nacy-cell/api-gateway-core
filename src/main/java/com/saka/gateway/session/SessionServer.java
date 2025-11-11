package com.saka.gateway.session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.Channel;

import java.net.InetSocketAddress;
import java.util.concurrent.Callable;


public class SessionServer implements Callable<Channel> {

    private final Logger logger = LoggerFactory.getLogger(SessionServer.class);

    private final EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    private final EventLoopGroup workerGroup = new NioEventLoopGroup();
    private  Channel serverChannel;
    private final Configuration configuration;

    public SessionServer(Configuration configuration){
        this.configuration = configuration;
    }

    @Override
    public Channel call() throws Exception {
        ChannelFuture channelFuture = null;

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class) // 指定服务端通道类型
                    .option(ChannelOption.SO_BACKLOG,128)// 设置 TCP 积压队列大小
                    .childHandler(new SessionChannelInitializer(configuration));
            channelFuture = bootstrap.bind(new InetSocketAddress(7397)).syncUninterruptibly();
            this.serverChannel = channelFuture.channel();
        } catch (Exception e) {
            logger.error("socket server start error.", e);
        }finally {
            if (null != channelFuture && channelFuture.isSuccess())
                logger.info("socket server start done.");
           else
               logger.error("socket server start error.");
        }

        return serverChannel;
    }
}
