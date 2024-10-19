package com.api.demo;

import org.json.JSONObject;
import org.testng.Reporter;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class APIDemo {
	
	@BeforeSuite
	public void getBaseURI() {
		RestAssured.baseURI="https://reqres.in";
	}
	
	@Test(enabled = false)
	public void createuser() {
		Response res =RestAssured.given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(getBody())
				/*
				 .body("{\r\n"
					+ "    \"name\": \"sakthi\",\r\n"
					+ "    \"job\": \"leader\"\r\n"
					+ "}")
				 */
				.when()
				.post("api/users");

	}
	
	@Test(enabled = true)
	public void createUserUsingJson() {
		Response res =RestAssured.given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(getJsonBody().toString())
				
				.when()
				.post("https://reqres.in/api/users");
		res.then().log().all();

	}
	
	@Test
	public void createUserUsingFormParam() {
		Response res =RestAssured.given()
				.contentType(ContentType.MULTIPART)
				.config(RestAssuredConfig.config().encoderConfig(EncoderConfig.encoderConfig().encodeContentTypeAs("multipart/form-data", ContentType.TEXT)))
//				.accept(ContentType.JSON)
				.formParam("name", "sakthi")
				.formParam("Job", "Test Engineer")
				.when()
				.post("https://reqres.in/api/users");
		res.then().log().all();

	}
	
	public String getBody() {
		String body="{\r\n"
				+ "    \"name\": \"sakthi\",\r\n"
				+ "    \"job\": \"leader\"\r\n"
				+ "}";
		return body;
		
	}
	
	public JSONObject getJsonBody() {
		
		JSONObject jsonBody = new JSONObject();
		
		jsonBody.put("name", "sakthi");
		jsonBody.put("job", "Test Engineer");
		
		return jsonBody;
	}

}
