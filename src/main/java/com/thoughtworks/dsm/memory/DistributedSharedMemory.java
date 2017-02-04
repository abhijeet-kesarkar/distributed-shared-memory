package com.thoughtworks.dsm.memory;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * Created by abhijeek on 22/01/17.
 */
@Component
public class DistributedSharedMemory {

    private HashMap <Integer, Integer> map = new HashMap<>();

    private int port;
    private int remotePorts;

    public DistributedSharedMemory(@Value("${cache.port}") int port, @Value("${cache.remote.ports}") int remotePorts) {
        this.port = port;
        this.remotePorts = remotePorts;
        Receiver.listen(this.port, this);
    }

    public void write(int key, int value){

        map.put(key, value);
        Sender.forPort(remotePorts).put(key, value);
    }

    public int read(int key){
        Integer value = null;
        if(value == null){
            value = Sender.forPort(remotePorts).get(key);
        }
        return value;
    }

    void localWrite(int key, int value){
        map.put(key, value);
    }

    Integer localRead(int key){
        return map.get(key);
    }
}
