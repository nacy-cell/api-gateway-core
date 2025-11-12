package com.saka.gateway.socket.handlers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.saka.gateway.bind.IGenericReference;
import com.saka.gateway.session.GatewaySession;
import com.saka.gateway.session.defaults.DefaultGatewaySessionFactory;
import com.saka.gateway.socket.BaseHandler;
import com.saka.gateway.socket.aggrement.RequestParser;
import com.saka.gateway.socket.aggrement.ResponseParser;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class GatewayServerHandler extends BaseHandler<FullHttpRequest> {

    private final Logger logger = LoggerFactory.getLogger(GatewayServerHandler.class);

    private final DefaultGatewaySessionFactory gatewaySessionFactory;

    public GatewayServerHandler(DefaultGatewaySessionFactory gatewaySessionFactory) {
        this.gatewaySessionFactory = gatewaySessionFactory;
    }

    @Override
    protected void session(ChannelHandlerContext ctx, final Channel channel, FullHttpRequest request) {
        logger.info("网关接收请求 uri：{} method：{}", request.uri(), request.method());

        // 1. 解析请求参数
        RequestParser requestParser = new RequestParser(request);
        String uri = requestParser.getUri();
        if (null == uri) return;
        Map<String, Object> args = new RequestParser(request).parse();

        // 2. 调用会话服务
        GatewaySession gatewaySession = gatewaySessionFactory.openSession(uri);
        IGenericReference reference = gatewaySession.getMapper();
        Object result = reference.$invoke(args);

        // 3. 封装返回结果
        DefaultFullHttpResponse response = new ResponseParser().parse(result);
        channel.writeAndFlush(response);
    }

}

