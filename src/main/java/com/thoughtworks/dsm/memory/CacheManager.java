package com.thoughtworks.dsm.memory;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * Created by abhijeek on 22/01/17.
 */
@Component
public class CacheManager {

    private HashMap <Integer, Integer> map = new HashMap<>();

    private int port;
    private int remotePorts;

    public CacheManager(@Value("${cache.port}") int port, @Value("${cache.remote.ports}") int remotePorts) {
        this.port = port;
        this.remotePorts = remotePorts;
        System.out.println("cache port " + this.port);
        System.out.println("remote port " + this.remotePorts);
        Receiver.listen(this.port);
    }

    public void put(int key, int value){

        System.out.println("cache port " + port);
        System.out.println("remote port " + remotePorts);
        map.put(key, value);
        Sender.forPort(remotePorts).put(key, value);
    }

    public int get(int key){
        return map.get(key);
    }
}
