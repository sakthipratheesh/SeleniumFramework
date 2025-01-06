package com.api.utilites;

public class RequestBodyUtils {

	public static String createUserBody(String name, String job) {
		String body="{\r\n"
				+ "    \"name\": \""+name+"\",\r\n"
				+ "    \"job\": \""+job+"\"\r\n"
				+ "}";
		return body;
	
	}

}
