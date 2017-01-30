package com.thoughtworks.dsm.application;

import com.thoughtworks.dsm.memory.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by abhijeek on 21/01/17.
 */
@RestController
@RequestMapping("/app")
public class App {


    private final CacheManager cacheManager;

    @Autowired
    public App(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @RequestMapping(value = "/value/{key}", method = RequestMethod.GET)
    public Integer getValue(@PathVariable("key") int key ) {

        System.out.println("key = [" + key + "]");
        return cacheManager.get(key);

    }

    @RequestMapping(value = "/value/{key}", method = RequestMethod.POST)
    public void setStatus(@PathVariable("key") int key, @RequestBody Integer value) {

        System.out.println("key = [" + key + "], value = [" + value + "]");
        cacheManager.put(key, value);
    }

}
