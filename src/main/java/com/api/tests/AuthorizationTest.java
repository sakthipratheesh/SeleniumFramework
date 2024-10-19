package com.api.tests;

import static org.hamcrest.Matchers.equalTo;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.api.routes.BasicRoutes;
import com.api.utilites.SchemaValidation;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class AuthorizationTest {

	@Test
	public void testAPIAuthPositive() {
		Reporter.log("API Call for API Authentication");
		Reporter.log("Performing API call");
		String apiKey = "46e5794ec313c4ab6218d9a801f512fc";
		Response response =RestAssured.given()
				.pathParam("mypath", "data/2.5/weather")
				.queryParam("q", "Delhi")
				.queryParam("appid",apiKey)
				
		.when()
			.get(BasicRoutes.api_auth_url+"/{mypath}");
		response.then()
		.log().all()
		.assertThat().body("name", equalTo("Delhi"));
		
		Assert.assertEquals(response.getStatusCode(), 200);
		Reporter.log("Request Headers :"+ response.getHeaders().toString());
		Reporter.log("Request Body :"+ response.asString());
		
		new SchemaValidation().isSchemaValid("API Authorization", "200", response.asString());
	}
	
	@Test
	public void testAPIAuthNegative() {
		Reporter.log("API Call for API Authentication");
		Reporter.log("Performing API call");
		String apiKey = "46e5794ec313c4ab6218d9a801f512f";
		Response response =RestAssured.given()
				.pathParam("mypath", "data/2.5/weather")
				.queryParam("q", "Delhi")
				.queryParam("appid",apiKey)
				
		.when()
			.get(BasicRoutes.api_auth_url+"/{mypath}");
		response.then()
		.log().all();
//		.assertThat().body("name", equalTo("Delhi"));
		
		Assert.assertEquals(response.getStatusCode(), 401);
		Reporter.log("Request Headers :"+ response.getHeaders().toString());
		Reporter.log("Request Body :"+ response.asString());
		
		new SchemaValidation().isSchemaValid("API Authorization", "401", response.asString());
	}
}
