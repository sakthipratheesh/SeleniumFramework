package com.api.tests;

import static org.hamcrest.Matchers.equalTo;

import java.util.HashMap;
import java.util.Map;

import org.skyscreamer.jsonassert.JSONAssert;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.api.payload.UserData;
import com.api.routes.BasicRoutes;
import com.api.utilites.RequestBodyUtils;
import com.api.utilites.SchemaValidation;
import com.automation.reports.ExtentTestNGListenerAPI;
import com.automation.utils.AssertionUtils;
import com.automation.utils.ConfigReader;
import com.automation.utils.RestAssuredUtils;
import com.aventstack.extentreports.model.Report;
import com.github.javafaker.Faker;


import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ApiTestDemo {
	
	private String name;
	private String job;
	private String city;
	private String trueApiKey = ConfigReader.getProperty("openWeatherTrueApiKey");
	private String falseApiKey = ConfigReader.getProperty("openWeatherFalseApiKey");
	private String WeatherApiPath = ConfigReader.getProperty("WeatherApiPath"); 
	
	UserData body ;
	SchemaValidation validator =new SchemaValidation();
	public  ExtentTestNGListenerAPI report = new ExtentTestNGListenerAPI();
	@BeforeSuite
	public void generateData() {
			
		body = new UserData();
		
		Faker data = new Faker();
		
		this.name = data.name().fullName();
		this.job = data.job().field();
		this.city = data.address().city();
		
		body.setName(name); 
		body.setJob(job);
		
		
	}
	
	@Test(dataProvider = "userData", dataProviderClass = com.api.data.DataProviders.class)
    public void createUser(String name, String job) {
    	report.logInfo("user Creation using Data Provider ");
        String body = RequestBodyUtils.createUserBody(name, job);
        Map<String, String> headers = new HashMap<>();
        headers.put("content-type", "application/json");
        
        Response res = RestAssuredUtils.sendPostRequest(BasicRoutes.userCreationBaseURI,BasicRoutes.userCreateEndpoint,headers, body);
        
        res.then().statusCode(201);
        report.logInfo("User creation is successfull ");
        report.logInfo(res.asString());
        Assert.assertEquals(res.getStatusCode(), 201);
        // Schema validation
        validator.isSchemaValid("postUser", "201", res.asString());
//        AssertionUtils.assertEquals(validator.isSchemaValid("postUser", "201", res.asString()), "User creation using Post");
    }
	
	
	@Test
    public void createUserWithFaker() {
    	report.logInfo("user Creation using Data Faker ");
        String body = RequestBodyUtils.createUserBody(this.name, this.job);
        Map<String, String> headers = new HashMap<>();
        headers.put("content-type", "application/json");
        
        Response res = RestAssuredUtils.sendPostRequest(BasicRoutes.userCreationBaseURI,BasicRoutes.userCreateEndpoint,headers, body);
        
		res.then().log().all()
		.statusCode(201)
		.statusLine("HTTP/1.1 201 Created")
		.body("name", equalTo(this.name));
        report.logInfo("User creation is successfull ");
        report.logInfo("Response Header"+ res.getHeaders().toString());
        report.logInfo("REsposne Body"+ res.getBody().asPrettyString());
		
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
		validator.isSchemaValid("postUser", "201", res.asString());
//		AssertionUtils.assertEquals(validator.isSchemaValid("postUser", "201", res.asString()), "User creation using Post");
	}
	
	@Test
	public void testAPIAuthPositive() {
		report.logInfo("API Call for API Authentication");
		Map<String, String> queryParam = new HashMap<>();
//        queryParam.put("mypath",WeatherApiPath );
		queryParam.put("q", "London");
		queryParam.put("appid", this.trueApiKey);
        Response response = RestAssuredUtils.sendGetRequest(BasicRoutes.api_auth_url,WeatherApiPath,queryParam);

		response.then()
		.log().all()
		.assertThat().body("name", equalTo("London"));
		
		
		Assert.assertEquals(response.getStatusCode(), 200);
		report.logInfo("Request Headers :"+ response.getHeaders().toString());
		report.logInfo("Request Body :"+ response.asString());
		validator.isSchemaValid("API Authorization", "200", response.asString());
		AssertionUtils.assertEquals(validator.isSchemaValid("API_Weather_Schema", "200", response.asString()),"200 Weather API validation");
	}
	
	@Test
	public void testAPIAuthNegative() {
		report.logInfo("API Call for API Authentication");
		report.logInfo("Performing API call");
		Map<String, String> queryParam = new HashMap<>();
//		queryParam.put("mypath", WeatherApiPath);
		queryParam.put("q", "London");
		queryParam.put("appid", this.falseApiKey);
        Response response = RestAssuredUtils.sendGetRequest(BasicRoutes.api_auth_url,WeatherApiPath,queryParam);

		response.then()
		.log().all();
		
		
		Assert.assertEquals(response.getStatusCode(), 401);
		report.logInfo("Request Headers :"+ response.getHeaders().toString());
		report.logInfo("Request Body :"+ response.asString());
		validator.isSchemaValid("API Authorization", "401", response.asString());
		AssertionUtils.assertEquals(validator.isSchemaValid("API Authorization", "401", response.asString()),"401 Weather API validation");
	}
	
}
