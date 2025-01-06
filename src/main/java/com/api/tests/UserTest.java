package com.api.tests;

import static org.hamcrest.Matchers.equalTo;

import org.skyscreamer.jsonassert.JSONAssert;
import org.testng.Reporter;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;


import com.api.payload.UserData;
import com.api.routes.BasicRoutes;
import com.api.utilites.ReadExcelData;
import com.api.utilites.SchemaValidation;
import com.github.javafaker.Faker;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class UserTest {
	public String name;
	public String job;
	
	UserData body ;
	
	@BeforeSuite
	public void getBaseURI() {
		RestAssured.baseURI=BasicRoutes.userCreationBaseURI;
		
		body = new UserData();
		
		Faker data = new Faker();
		
		this.name = data.name().fullName();
		this.job = data.job().field();
		
		body.setName(name); 
		body.setJob(job);
		
		
	}

	@Test(dataProvider = "userInput" , dataProviderClass = ReadExcelData.class)
	public void createUserUsingExcel(String name , String job) {
		Reporter.log("user Creation using Excel Data");
		Response res =RestAssured.given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(body.getBody(name , job))
				.when()
				.post(BasicRoutes.userCreateEndpoint);
		
		res.then().log().all()
		.statusCode(201)
		.statusLine("HTTP/1.1 201 Created")
		.body("name", equalTo(name));
		
		Reporter.log("Response Header"+ res.getHeaders().toString());
		Reporter.log("REsposne Body"+ res.getBody().asPrettyString());
		// Fetching the Data from Response
		JsonPath jsonPath = new JsonPath(res.asString());
		String id = jsonPath.getString("id");
		String createdAt = jsonPath.getString("createdAt");
		
		
		String expectedResponse = "{\r\n"
				+ "    \"name\": \""+name+"\",\r\n"
				+ "    \"job\": \""+job+"\",\r\n"
				+ "    \"id\": \""+id+"\",\r\n"
				+ "    \"createdAt\": \""+createdAt+"\"\r\n"
				+ "}";
		
		String actualResponse = res.asString();
		
		//Response validation 
		JSONAssert.assertEquals(expectedResponse, actualResponse, false);
		
		//Schema Validation		
		new SchemaValidation().isSchemaValid("postUser", "201", res.asString());
		
	}
	
	@Test
	public void createUserUsingFaker() {
		Reporter.log("user Creation using Faker");
		Response res =RestAssured.given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(body.getBody(this.name , this.job))
				.when()
				.post(BasicRoutes.userCreateEndpoint);
		
		res.then().log().all()
		.statusCode(201)
		.statusLine("HTTP/1.1 201 Created")
		.body("name", equalTo(this.name));
		
		Reporter.log("Response Header"+ res.getHeaders().toString());
		Reporter.log("REsposne Body"+ res.getBody().asPrettyString());
		
		// Fetching the Data from Response
		JsonPath jsonPath = new JsonPath(res.asString());
		String id = jsonPath.getString("id");
		String createdAt = jsonPath.getString("createdAt");
		
		
		String expectedResponse = "{\r\n"
				+ "    \"name\": \""+this.name+"\",\r\n"
				+ "    \"job\": \""+this.job+"\",\r\n"
				+ "    \"id\": \""+id+"\",\r\n"
				+ "    \"createdAt\": \""+createdAt+"\"\r\n"
				+ "}";
		
		String actualResponse = res.asString();
		
		//Response validation 
		JSONAssert.assertEquals(expectedResponse, actualResponse, false);
		
		//Schema Validation		
		new SchemaValidation().isSchemaValid("postUser", "201", res.asString());
		
	}
	
	@Test
	public void createUserUsingJsonBody() {
		Reporter.log("user Creation using JSON Body");
		Response res =RestAssured.given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(body.getJsonBody().toString())
				.when()
				.post(BasicRoutes.userCreateEndpoint);
		
		res.then().log().all()
		.statusCode(201)
		.statusLine("HTTP/1.1 201 Created")
		.body("name", equalTo(body.getName()));
		
		Reporter.log("Response Header"+ res.getHeaders().toString());
		Reporter.log("REsposne Body"+ res.getBody().asPrettyString());
		
		// Fetching the Data from Response
		JsonPath jsonPath = new JsonPath(res.asString());
		String id = jsonPath.getString("id");
		String createdAt = jsonPath.getString("createdAt");
		
		
		String expectedResponse = "{\r\n"
				+ "    \"name\": \""+body.getName()+"\",\r\n"
				+ "    \"job\": \""+body.getJob()+"\",\r\n"
				+ "    \"id\": \""+id+"\",\r\n"
				+ "    \"createdAt\": \""+createdAt+"\"\r\n"
				+ "}";
		
		String actualResponse = res.asString();
		
		//Response validation 
		JSONAssert.assertEquals(expectedResponse, actualResponse, false);
		
		//Schema Validation		
		new SchemaValidation().isSchemaValid("postUser", "201", res.asString());
		
	}
}
