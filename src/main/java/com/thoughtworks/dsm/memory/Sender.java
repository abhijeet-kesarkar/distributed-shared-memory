package com.thoughtworks.dsm.memory;

import com.thoughtworks.dsm.memory.messages.GetRequest;
import com.thoughtworks.dsm.memory.messages.PutRequest;
import com.thoughtworks.dsm.memory.stub.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
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

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Created by abhijeek on 29/01/17.
 */
public class Sender {


    private static final Logger logger = Logger.getLogger(Sender.class.getName());

    private int port;

    static final String HOST = System.getProperty("host", "127.0.0.1");

    public static Sender forPort(int port){
        return new Sender(HOST, port);
    }


    public void put(int key, int value) {

        try{
            logger.info("Will try to put " + key + " ..." + value);
            WriteRequest request = WriteRequest.newBuilder().setKey(key).setValue(value).build();
            Empty response;
            try {
                response = blockingStub.write(request);
            } catch (StatusRuntimeException e) {
                logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
            }
        }
        finally{
            try {
                shutdown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private final ManagedChannel channel;
    private final DistributedMemoryServiceGrpc.DistributedMemoryServiceBlockingStub blockingStub;

    /** Construct client connecting to HelloWorld server at {@code host:port}. */
    private Sender(String host, int port) {
        channel = ManagedChannelBuilder.forAddress(host, port)
                // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
                // needing certificates.
                .usePlaintext(true)
                .build();
        blockingStub = DistributedMemoryServiceGrpc.newBlockingStub(channel);
    }

    public void shutdown() throws InterruptedException {
        logger.info("shutdown sender");
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public int get(int key) {
        try{
            logger.info("Will try to get " + key + " ...");
            ReadRequest request = ReadRequest.newBuilder().setKey(key).build();
            ReadResponse response;
            try {
                response = blockingStub.read(request);
            } catch (StatusRuntimeException e) {
                logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
                return -1;
            }
            logger.info("Response: " + response.getValue());
            return response.getValue();
        }
        finally{
            try {
                shutdown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
