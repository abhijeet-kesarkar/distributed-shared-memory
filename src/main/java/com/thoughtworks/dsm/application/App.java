package com.thoughtworks.dsm.application;

import com.thoughtworks.dsm.memory.DistributedSharedMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by abhijeek on 21/01/17.
 */
@RestController
@RequestMapping("/app")
public class App {


    private final DistributedSharedMemory distributedSharedMemory;

    @Autowired
    public App(DistributedSharedMemory distributedSharedMemory) {
        this.distributedSharedMemory = distributedSharedMemory;
    }

    @RequestMapping(value = "/value/{key}", method = RequestMethod.GET)
    public Integer getValue(@PathVariable("key") int key ) {

        System.out.println("key = [" + key + "]");
        return distributedSharedMemory.read(key);

    }

    @RequestMapping(value = "/value/{key}", method = RequestMethod.POST)
    public void setStatus(@PathVariable("key") int key, @RequestBody Integer value) {

        System.out.println("key = [" + key + "], value = [" + value + "]");
        distributedSharedMemory.write(key, value);
    }

}
