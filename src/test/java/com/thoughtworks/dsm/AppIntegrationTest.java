package com.thoughtworks.dsm;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

/**
 * Created by abhijeek on 21/01/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AppIntegrationTest {

    @LocalServerPort
    private int port;


    @Test
    public void testSetValue(){
        given()
                .port(port)
        .when()
                .get("/stock/value/12")
        .then()
                .body(equalTo("10.0"));
    }

}
