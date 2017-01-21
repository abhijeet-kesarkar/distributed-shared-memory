package com.thoughtworks.dm;

import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by abhijeek on 21/01/17.
 */
@RestController
@RequestMapping("/stock")
public class Stock {


    @RequestMapping(value = "/value/{stockId}", method = RequestMethod.GET)
    public Double getValue(@PathVariable("stockId") int stockId ) {

        System.out.println("stockId = [" + stockId + "]");
        return 10D;

    }

    @RequestMapping(value = "/value/{stockId}", method = RequestMethod.POST)
    public void setStatus(@PathVariable("stockId") int stockId, @RequestBody Double value) {

        System.out.println("stockId = [" + stockId + "], value = [" + value + "]");
    }

}
