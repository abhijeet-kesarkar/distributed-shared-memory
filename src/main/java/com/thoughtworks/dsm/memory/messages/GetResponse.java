package com.thoughtworks.dsm.memory.messages;

/**
 * Created by abhijeek on 29/01/17.
 */
public class GetResponse {

    private int value;

    public GetResponse(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
