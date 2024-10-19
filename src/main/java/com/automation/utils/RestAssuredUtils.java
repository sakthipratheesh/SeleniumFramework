package com.automation.utils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

import com.api.payload.UserData;

public class RestAssuredUtils {

	/*
    // Initialize base URL for the API
    public static void setBaseURI(String baseURI) {
        RestAssured.baseURI = baseURI;
    }

    // Build Request with Headers
    public static RequestSpecification buildRequest(Map<String, String> headers) {
        return RestAssured.given()
                          .contentType(ContentType.JSON)
                          .headers(headers);
    }
    
    */

    // GET Request with Authentication (Basic Auth)
    public static Response getWithBasicAuth(String endpoint, String username, String password) {
        return RestAssured.given()
                          .auth()
                          .preemptive()
                          .basic(username, password)
                          .when()
                          .get(endpoint)
                          .then()
                          .extract()
                          .response();
    }

    // GET Request with OAuth 2.0
    public static Response getWithOAuth(String endpoint, String token) {
        return RestAssured.given()
                          .auth()
                          .oauth2(token)
                          .when()
                          .get(endpoint)
                          .then()
                          .extract()
                          .response();
    }

    // GET Request without Authentication
    public static Response get(String endpoint) {
        return RestAssured.given()
                          .when()
                          .get(endpoint)
                          .then()
                          .extract()
                          .response();
    }

    // POST Request with Body and Authentication (Basic Auth)
    public static Response postWithBasicAuth(String endpoint, String username, String password, Object body) {
        return RestAssured.given()
                          .auth()
                          .preemptive()
                          .basic(username, password)
                          .body(body)
                          .when()
                          .post(endpoint)
                          .then()
                          .extract()
                          .response();
    }

    // POST Request with OAuth 2.0
    public static Response postWithOAuth(String endpoint, String token, Object body) {
        return RestAssured.given()
                          .auth()
                          .oauth2(token)
                          .body(body)
                          .when()
                          .post(endpoint)
                          .then()
                          .extract()
                          .response();
    }

    // POST Request without Authentication
    public static Response post(String endpoint, Object body) {
        return RestAssured.given()
                          .contentType(ContentType.JSON)
                          .body(body)
                          .when()
                          .post(endpoint)
                          .then()
                          .extract()
                          .response();
    }

    // PUT Request with Body and Authentication (Basic Auth)
    public static Response putWithBasicAuth(String endpoint, String username, String password, Object body) {
        return RestAssured.given()
                          .auth()
                          .preemptive()
                          .basic(username, password)
                          .body(body)
                          .when()
                          .put(endpoint)
                          .then()
                          .extract()
                          .response();
    }

    // PUT Request with OAuth 2.0
    public static Response putWithOAuth(String endpoint, String token, Object body) {
        return RestAssured.given()
                          .auth()
                          .oauth2(token)
                          .body(body)
                          .when()
                          .put(endpoint)
                          .then()
                          .extract()
                          .response();
    }

    // PUT Request without Authentication
    public static Response put(String endpoint, Object body) {
        return RestAssured.given()
                          .contentType(ContentType.JSON)
                          .body(body)
                          .when()
                          .put(endpoint)
                          .then()
                          .extract()
                          .response();
    }

    // DELETE Request with Authentication (Basic Auth)
    public static Response deleteWithBasicAuth(String endpoint, String username, String password) {
        return RestAssured.given()
                          .auth()
                          .preemptive()
                          .basic(username, password)
                          .when()
                          .delete(endpoint)
                          .then()
                          .extract()
                          .response();
    }

    // DELETE Request with OAuth 2.0
    public static Response deleteWithOAuth(String endpoint, String token) {
        return RestAssured.given()
                          .auth()
                          .oauth2(token)
                          .when()
                          .delete(endpoint)
                          .then()
                          .extract()
                          .response();
    }

    // DELETE Request without Authentication
    public static Response delete(String endpoint) {
        return RestAssured.given()
                          .when()
                          .delete(endpoint)
                          .then()
                          .extract()
                          .response();
    }

    // GET Request with Custom Headers
    public static Response getWithCustomHeaders(String endpoint, Map<String, String> headers) {
        return RestAssured.given()
                          .headers(headers)
                          .when()
                          .get(endpoint)
                          .then()
                          .extract()
                          .response();
    }

    // POST Request with Custom Headers
    public static Response postWithCustomHeaders(String endpoint, Map<String, String> headers, Object body) {
        return RestAssured.given()
                          .headers(headers)
                          .contentType(ContentType.JSON)
                          .body(body)
                          .when()
                          .post(endpoint)
                          .then()
                          .extract()
                          .response();
    }

    // PUT Request with Custom Headers
    public static Response putWithCustomHeaders(String endpoint, Map<String, String> headers, Object body) {
        return RestAssured.given()
                          .headers(headers)
                          .contentType(ContentType.JSON)
                          .body(body)
                          .when()
                          .put(endpoint)
                          .then()
                          .extract()
                          .response();
    }

    // DELETE Request with Custom Headers
    public static Response deleteWithCustomHeaders(String endpoint, Map<String, String> headers) {
        return RestAssured.given()
                          .headers(headers)
                          .when()
                          .delete(endpoint)
                          .then()
                          .extract()
                          .response();
    }

	public static Response sendPostRequest(String baseURI,String endpoint, Map<String, String> headers, String body) {
		return RestAssured.given()
				.baseUri(baseURI)
                .headers(headers)
                .body(body)
                .when()
                .post(endpoint)
                .then()
                .extract()
                .response();
	}
	
	public static Response sendGetRequest(String baseURI,String endpoint, Map<String, String> headers, String body) {
		return RestAssured.given()
				.baseUri(baseURI)
                .headers(headers)
                .body(body)
                .when()
                .get(endpoint)
                .then()
                .extract()
                .response();
	}

	public static Response sendGetRequest(String baseURI,String pathParam,Map<String, String> queryParam) {
		return RestAssured.given()
				.baseUri(baseURI)
				.queryParams(queryParam)
                .when()
                .get(pathParam)
                .then()
                .extract()
                .response();
	}
}
