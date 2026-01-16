package com.saka.gateway.core.socket.handlers;

import com.saka.gateway.core.limiter.strategy.LimitStrategy;
import com.saka.gateway.core.mapping.HttpStatement;
import com.saka.gateway.core.session.Configuration;
import com.saka.gateway.core.socket.BaseHandler;
import com.saka.gateway.core.socket.aggrement.AgreementConstants;
import com.saka.gateway.core.socket.aggrement.GatewayResultMessage;
import com.saka.gateway.core.socket.aggrement.ResponseParser;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LimitServerHandler extends BaseHandler<FullHttpRequest> {

    private final Logger logger = LoggerFactory.getLogger(LimitServerHandler.class);

    private Configuration configuration;

    public LimitServerHandler(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    protected void session(ChannelHandlerContext ctx, Channel channel, FullHttpRequest request) {
        HttpStatement httpStatement=channel.attr(AgreementConstants.HTTP_STATEMENT).get();
        try {
            if(httpStatement.isLimit()){
                int qps = httpStatement.getQps();
                int limitType = httpStatement.getLimitType();
                String methodId = httpStatement.getApplication() + "-" + httpStatement.getInterfaceName() + "-" + httpStatement.getMethodName() + "-" +httpStatement.getUri();
                LimitStrategy limitStrategy = configuration.buildLimiter(methodId, limitType, qps);
                boolean pass = limitStrategy.tryAcquire();

                if (pass) {
                    request.retain();
                    ctx.fireChannelRead(request);
                }
                else {
                    DefaultFullHttpResponse response = new ResponseParser().parse(GatewayResultMessage.buildError(AgreementConstants.ResponseCode._429.getCode(), AgreementConstants.ResponseCode._429.getInfo()));
                    channel.writeAndFlush(response);
                }
            }else {
                request.retain();
                ctx.fireChannelRead(request);
            }
        }catch (Exception e){
            DefaultFullHttpResponse response = new ResponseParser().parse(GatewayResultMessage.buildError(AgreementConstants.ResponseCode._500.getCode(), "网关协议调用失败！" + e.getMessage()));
            channel.writeAndFlush(response);
        }





    }
}
