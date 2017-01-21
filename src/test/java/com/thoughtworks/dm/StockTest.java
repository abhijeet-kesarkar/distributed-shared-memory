package com.thoughtworks.dm;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by abhijeek on 21/01/17.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class StockTest {

    @Autowired
    private Stock stock;

    @Test
    public void testSetValue(){
        stock.setStatus(12, 213.1);
    }

}