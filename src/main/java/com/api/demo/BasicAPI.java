package com.api.demo;
import static org.hamcrest.Matchers.equalTo;

import org.json.JSONObject;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class BasicAPI {
	
	@BeforeClass
	public void getBaseURI() {
		RestAssured.baseURI="https://reqres.in";
	}
	@Test
	public void createUser()
	{
		Response response =RestAssured.given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
			.body("{\r\n"
					+ "    \"name\": \"sakthi\",\r\n"
					+ "    \"job\": \"leader\"\r\n"
					+ "}")
		.when()
			.post("api/users");

		response.then().log().all()
		.statusCode(201)
		.statusLine("HTTP/1.1 201 Created")
		.contentType(ContentType.JSON)
		.body("name", equalTo("sakthi"));
		
		Reporter.log("URL :"+"https://reqres.in/api/users");
		Reporter.log("Response :"+response.asPrettyString());
		
	}
	@Test
	public void createUserNew()
	{
		ValidatableResponse response =RestAssured.given()
				.header("Content-Type", "application/json")
				.header("Accept", "application/json")
			.body(getBody())
		.when()
			.post("https://reqres.in/api/users")
			.then()
			.log().all()
			.statusCode(201)
			.statusLine("HTTP/1.1 201 Created")
			.contentType(ContentType.JSON)
			.body("name", equalTo("sakthi"));
		Reporter.log("URL :"+"https://reqres.in/api/users");
		Reporter.log("Response :"+response.toString());
		
	}
	@Test
	public void createUserNewUsingJson()
	{
		System.out.println("JSOn Body"+ getJsonBody());
		ValidatableResponse response =RestAssured.given()
				.header("Content-Type", "application/json")
				.header("Accept", "application/json")
			.body(getJsonBody().toString())
		.when()
			.post("api/users")
			.then()
			.log().all()
			.statusCode(201)
			.statusLine("HTTP/1.1 201 Created")
			.contentType(ContentType.JSON)
			.body("name", equalTo("sakthi"));
		Reporter.log("URL :"+"https://reqres.in/api/users");
		Reporter.log("Response :"+response.toString());
		
	}
	
	@Test
	public void createUserUsingFormParam()
	{
		ValidatableResponse response =RestAssured.given()
				.header("Content-Type", "multipart/form-data")
				.config(RestAssuredConfig.config().encoderConfig(EncoderConfig.encoderConfig().encodeContentTypeAs("multipart/form-data", ContentType.TEXT)))
				.formParam("Name", "Sakthi" )
				.formParam("Designation", "Test Engineer" )
//			.body(getBody())
		.when()
			.post("api/users")
			.then()
			.log().all()
			.statusCode(201)
			.statusLine("HTTP/1.1 201 Created");
//			.contentType(ContentType.MULTIPART);
//			.body("name", equalTo("sakthi"));
		Reporter.log("URL :"+"https://reqres.in/api/users");
		Reporter.log("Response :"+response.toString());
		
	}
	public static String getBody() {
		String createUserBody ="{\r\n"
				+ "    \"name\": \"sakthi\",\r\n"
				+ "    \"job\": \"Test Engineer\"\r\n"
				+ "}";
		
		return createUserBody;
	}
	
	public static JSONObject getJsonBody() {
		
		JSONObject requestBody = new JSONObject();
		
		requestBody.put("name", "sakthi");
		requestBody.put("job", "Test Engineer");
		return requestBody;
		
	}
}
