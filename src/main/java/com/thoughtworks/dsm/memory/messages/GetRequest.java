package com.thoughtworks.dsm.memory.messages;

/**
 * Created by abhijeek on 29/01/17.
 */
public class GetRequest {

    private int key;

    public GetRequest(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }
}
