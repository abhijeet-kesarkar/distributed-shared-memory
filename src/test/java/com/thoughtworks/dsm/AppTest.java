package com.thoughtworks.dsm;

import com.thoughtworks.dsm.application.App;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by abhijeek on 21/01/17.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppTest {

    @Autowired
    private App app;

    @Test
    public void testSetValue(){
        app.setStatus(12, 211);
    }

}