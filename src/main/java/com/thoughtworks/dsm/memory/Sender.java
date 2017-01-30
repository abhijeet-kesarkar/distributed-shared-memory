package com.thoughtworks.dsm.memory;

import com.thoughtworks.dsm.memory.messages.PutRequest;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;


/**
 * Created by abhijeek on 29/01/17.
 */
public class Sender {

    //@Value("${remoteport}")
    //private int port;

    private int port;

    private Sender(int port) {
        this.port = port;
    }

    public static Sender forPort(int port){
        return new Sender(port);
    }

    static final String HOST = System.getProperty("host", "127.0.0.1");
    static final int SIZE = Integer.parseInt(System.getProperty("size", "256"));

    public void put(int key, int value) {

        send(new PutRequest(key, value));
    }

    private void send(Object message) {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();

                            p.addLast(
                                    new ObjectEncoder(),
                                    new ObjectDecoder(ClassResolvers.cacheDisabled(null)),
                                    new ObjectEchoClientHandler(message));
                        }
                    });
            System.out.printf("Trying to connect to remote server %s %s" , HOST, port);

            // Start the connection attempt.
            b.connect(HOST, port).sync().channel().closeFuture().sync();

            System.out.printf("Connected to remote server %s %s" , HOST, port);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }
}
