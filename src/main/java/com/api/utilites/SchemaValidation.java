package com.api.utilites;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Reporter;

public class SchemaValidation {

	
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
                SchemaJson = basePath + File.separator + "schemaJson" + File.separator + "401_Authorization_Schema.json";
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
