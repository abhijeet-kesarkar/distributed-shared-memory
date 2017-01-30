package com.thoughtworks.dsm.memory.messages;

/**
 * Created by abhijeek on 29/01/17.
 */

public class PutRequest {

    int key;
    int value;

    public PutRequest(int key, int value) {
        this.key = key;
        this.value = value;
    }

    public int getKey() {
        return key;
    }

    public int getValue() {
        return value;
    }
}
