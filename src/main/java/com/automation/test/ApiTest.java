package com.automation.test;

import com.automation.utils.RestAssuredUtils;


import io.restassured.response.Response;


import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ApiTest {

    private static String baseURI = "https://api.example.com";
    private static String basicAuthUsername = "username";
    private static String basicAuthPassword = "password";
    private static String oAuthToken = "Bearer token_value";

//    @BeforeClass
//    public void setup() {
//        RestAssuredUtils.setBaseURI(baseURI);
//    }

    @Test
    public void testGetWithBasicAuth() {
        Response response = RestAssuredUtils.getWithBasicAuth("/users/1", basicAuthUsername, basicAuthPassword);
        Assert.assertEquals(response.getStatusCode(), 200);
        System.out.println("Response Body: " + response.getBody().asString());
    }

    @Test
    public void testPostWithOAuth() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", "John Doe");
        requestBody.put("email", "johndoe@example.com");

        Response response = RestAssuredUtils.postWithOAuth("/users", oAuthToken, requestBody);
        Assert.assertEquals(response.getStatusCode(), 201);
        System.out.println("Response Body: " + response.getBody().asString());
    }

    @Test
    public void testPutWithoutAuth() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", "John Updated");

        Response response = RestAssuredUtils.put("/users/1", requestBody);
        Assert.assertEquals(response.getStatusCode(), 200);
        System.out.println("Response Body: " + response.getBody().asString());
    }

    @Test
    public void testDeleteWithCustomHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer customToken");

        Response response = RestAssuredUtils.deleteWithCustomHeaders("/users/1", headers);
        Assert.assertEquals(response.getStatusCode(), 204);
        System.out.println("Delete successful");
    }

    @Test
    public void testGetWithCustomHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Custom-Header", "HeaderValue");

        Response response = RestAssuredUtils.getWithCustomHeaders("/endpoint", headers);
        Assert.assertEquals(response.getStatusCode(), 200);
        System.out.println("Response Body: " + response.getBody().asString());
    }
}
