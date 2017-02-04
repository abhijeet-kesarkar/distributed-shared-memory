package com.thoughtworks.dsm.memory;

import com.thoughtworks.dsm.memory.stub.*;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by abhijeek on 27/01/17.
 */
public class Receiver extends Thread{

    private int PORT;

    private DistributedSharedMemory distributedSharedMemory;

    public Receiver(int PORT, DistributedSharedMemory distributedSharedMemory) {
        this.PORT = PORT;
        this.distributedSharedMemory = distributedSharedMemory;
    }


    public void run(){
        try {
            this.startServer();
            this.blockUntilShutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void listen(int port, DistributedSharedMemory distributedSharedMemory){
        final Receiver receiver = new Receiver(port, distributedSharedMemory);
        receiver.start();

    }

    private static final Logger logger = Logger.getLogger(Receiver.class.getName());

    /* The port on which the server should run */
    private Server server;

    private void startServer() throws IOException {
        server = ServerBuilder.forPort(PORT)
                .addService(new DistributedMemoryImpl())
                .build()
                .start();
        logger.info("Server started, listening on " + PORT);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                Receiver.this.stopServer();
                System.err.println("*** server shut down");
            }
        });
    }

    private void stopServer() {
        if (server != null) {
            server.shutdown();
        }
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon threads.
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    private class DistributedMemoryImpl extends DistributedMemoryServiceGrpc.DistributedMemoryServiceImplBase {


        @Override
        public void read(ReadRequest request, StreamObserver<ReadResponse> responseObserver) {
            System.out.println("read request = " + request.getKey());
            int value = distributedSharedMemory.localRead(request.getKey());
            ReadResponse response = ReadResponse.newBuilder().setValue(value).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();

        }

        @Override
        public void write(WriteRequest request, StreamObserver<Empty> responseObserver) {
            System.out.println("write request = " + request.getKey() + " " + request.getValue());
            distributedSharedMemory.localWrite(request.getKey(), request.getValue());
            responseObserver.onNext(Empty.getDefaultInstance());
            responseObserver.onCompleted();

        }
    }

}
