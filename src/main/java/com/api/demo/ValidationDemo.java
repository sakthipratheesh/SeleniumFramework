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
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ValidationDemo {
	
	public String name;
	public String job;
	
	@BeforeSuite
	public void getBaseURI() {
		RestAssured.baseURI="https://reqres.in";
		
		Faker data = new Faker();
		
		this.name = data.name().fullName();
		this.job = data.job().field();
		
	}
	
	@Test
	public void createuser() {
		Response res =RestAssured.given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(getBody())
				.when()
				.post("api/users");
		
		res.then().log().all()
		.statusCode(201)
		.statusLine("HTTP/1.1 201 Created")
		.body("name", equalTo(this.name));
		
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
		
		// RestAssured Schema Validator
		
		InputStream postUserCreationJsonSchema = getClass().getClassLoader().getResourceAsStream("schemaJson/postUser.json");
		res.then().body(JsonSchemaValidator.matchesJsonSchema(postUserCreationJsonSchema));

		//Ever it Schema Validation
		
		isSchemaValid("postUser", "201", res.asString());
	}
	
	public String getBody() {
		String body="{\r\n"
				+ "    \"name\": \""+this.name+"\",\r\n"
				+ "    \"job\": \""+this.job+"\"\r\n"
				+ "}";
		return body;
		
	}
	
	 /**
     * To Validate response schema
     * 
     * @param serviceName
     * @param statusCode
     * @param response
     * @return
     * @throws IOException
     */
    public boolean isSchemaValid( String serviceName, String statusCode, String response ) {
        boolean isSchemaValid = false;
        String basePath;
        try {
            basePath = new File( "." ).getCanonicalPath() + File.separator + "src" + File.separator + "main" + File.separator + "java" ;

            String SchemaJson = "";

            if ( statusCode.startsWith( "2" ) ) {
                SchemaJson = basePath + File.separator + "schemaJson" + File.separator + serviceName + ".json";
            } else if ( statusCode.startsWith( "4" ) ) {
                SchemaJson = basePath + File.separator + "schemaJson" + File.separator + "400_Schema.json";
            } else {
                SchemaJson = basePath + File.separator + "schemaJson" + File.separator + "500_Schema.json";
            }

            FileReader reader = new FileReader( SchemaJson );

            JSONObject jsonSchema = new JSONObject( new JSONTokener( reader ) );
            Schema schema = SchemaLoader.load( jsonSchema );

            schema.validate( new JSONObject( response ) );
            isSchemaValid = true;
            System.out.println("Schema validation Passed");
            Reporter.log( serviceName + "_" + statusCode + "-Schema is valid" );
        } catch ( IOException e ) {
        	Reporter.log( "Schema validation exception : " + e );
        }

        return isSchemaValid;
    }
	
}
