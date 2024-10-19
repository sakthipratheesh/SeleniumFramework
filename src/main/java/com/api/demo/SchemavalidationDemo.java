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
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class SchemavalidationDemo {

	public String name;
	public String job;
	
	@BeforeClass
	public void getBaseURI() {
		RestAssured.baseURI="https://reqres.in";
		
		Faker data = new Faker();
		
		this.name=data.name().fullName();
		this.job = data.job().field();
	}
	@Test
	public void createUser()
	{
		Response response =RestAssured.given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
			.body(getBody())
		.when()
			.post("api/users");

		//basic validations
		response.then().log().all()
		.statusCode(201)
		.statusLine("HTTP/1.1 201 Created")
		.contentType(ContentType.JSON)
		.body("name", equalTo(this.name));
		
		Reporter.log("URL :"+"https://reqres.in/api/users");
		Reporter.log("Response :"+response.asPrettyString());
		
		//Fetching data from Response
		JsonPath js = new JsonPath(response.asString());
		String id = js.getString("id");
		String createdDate = js.getString("createdAt");
		
		//Performing REsponse validation using JSONAssert
		String expectedResponse = "{\r\n"
				+ "    \"name\": \""+this.name+"\",\r\n"
				+ "    \"job\": \""+this.job+"\",\r\n"
				+ "    \"id\": \""+id+"\",\r\n"
				+ "    \"createdAt\": \""+createdDate+"\"\r\n"
				+ "}";
		String actualResponse = response.asString();		
		JSONAssert.assertEquals(expectedResponse, actualResponse, false);

		//Ever it Schema Validation
		isSchemaValid("userCreation", "201", response.asString());
		
		//RestAssured Schema Validator
		
		InputStream userCreationJsonSchema = getClass ().getClassLoader ().getResourceAsStream ("schemaJson/userCreation.json");
		response.then().body(JsonSchemaValidator.matchesJsonSchema(userCreationJsonSchema));
		
	}
	
	public String getBody() {
		String createUserBody ="{\r\n"
				+ "    \"name\": \""+this.name+"\",\r\n"
				+ "    \"job\": \""+this.job+"\"\r\n"
				+ "}";
		
		return createUserBody;
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
            Reporter.log( serviceName + "_" + statusCode + "-Schema is valid" );
        } catch ( IOException e ) {
        	Reporter.log( "Schema validation exception : " + e );
        }

        return isSchemaValid;
    }
}
