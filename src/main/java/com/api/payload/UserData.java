package com.api.payload;

import org.json.JSONObject;

public class UserData {

	private String name ;
	
	private String job;
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setJob(String job) {
		this.job = job;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getJob() {
		return this.job;
	}
	
	
	public String getBody(String name , String job) {
		String body="{\r\n"
				+ "    \"name\": \""+name+"\",\r\n"
				+ "    \"job\": \""+job+"\"\r\n"
				+ "}";
		return body;
		
	}
	
	public JSONObject getJsonBody() {
		
		JSONObject jsonBody = new JSONObject();
		
		jsonBody.put("name", getName());
		jsonBody.put("job", getJob());
		
		return jsonBody;
	}

}
