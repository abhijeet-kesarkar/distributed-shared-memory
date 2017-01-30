package com.thoughtworks.dsm.memory;

import com.thoughtworks.dsm.memory.messages.PutRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by abhijeek on 29/01/17.
 */
public class ObjectEchoClientHandler extends ChannelInboundHandlerAdapter {

    private Object message;

    public ObjectEchoClientHandler(Object message) {
        this.message = message;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {

        ctx.writeAndFlush(message);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}