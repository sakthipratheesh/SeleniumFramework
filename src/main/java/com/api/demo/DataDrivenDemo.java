package com.api.demo;

import static org.hamcrest.Matchers.equalTo;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.skyscreamer.jsonassert.JSONAssert;
import org.testng.Reporter;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class DataDrivenDemo {
	
	public String name;
	public String job;
	
	@BeforeSuite
	public void getBaseURI() {
		RestAssured.baseURI="https://reqres.in";
		
		Faker data = new Faker();
		
		this.name = data.name().fullName();
		this.job = data.job().field();
		
	}
	
	@Test(dataProvider = "userData")
	public void createuser(String name , String job) {
		Response res =RestAssured.given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(getBody(name , job))
				.when()
				.post("api/users");
		
		res.then().log().all()
		.statusCode(201)
		.statusLine("HTTP/1.1 201 Created")
		.body("name", equalTo(name));
		
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
		
	}
	
	public String getBody(String name , String job) {
		String body="{\r\n"
				+ "    \"name\": \""+name+"\",\r\n"
				+ "    \"job\": \""+job+"\"\r\n"
				+ "}";
		return body;
		
	}
	
	@DataProvider(name = "userData")
		public Object[][] InputData() {
		Object[][]  data= {
				{this.name , this.job},
				{"Sakthi" , "Test Engineer"},
				
		};
		return data; 
	}
	
	@Test(dataProvider = "userInput" , dataProviderClass = ReadExcelData.class)
	public void createUserUsingExcel(String name , String job) {
		Response res =RestAssured.given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(getBody(name , job))
				.when()
				.post("api/users");
		
		res.then().log().all()
		.statusCode(201)
		.statusLine("HTTP/1.1 201 Created")
		.body("name", equalTo(name));
		
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
		
	}

}
