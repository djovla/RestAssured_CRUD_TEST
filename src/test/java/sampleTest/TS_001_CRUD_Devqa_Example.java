package sampleTest;

import org.testng.annotations.Test;

import flexjson.JSONDeserializer;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;

public class TS_001_CRUD_Devqa_Example {
	/**
	 * Global Variable response will get the response from the web application
	 * request will send the request to the application regarding the method choose
	 * inputBody contain the JSON data and can be use to include modification in the
	 * JSON data JSONObject to get the data instead of using a String
	 **/
	private Response response;
	private RequestSpecification request;
	private String inputBody;
	private JSONObject requestParams = new JSONObject();

	/** Method initialize before all the test method **/
	@BeforeClass
	public void beforeClass() {
		// Specify the base URL to the RestFul web Service
		RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

		// getting the (RequestSpecification)->request to direct to the server
		request = RestAssured.given();

		// add a header to inform that the request body is a JSON
		request.header("Content-Type", "application/json");

		// passing to the the JSON data to the variable
		inputBody = "{\n" +
				"  \"title\": \"Developper\",\n"+
				"  \"body\": \"Java\",\n"+
				"  \"userId\": \"1\"\n}";
	}

	/** METHOD GET **/
	@Test(priority = 1)
	public void getDataApi() {
		// Specify the parameter to get only the objects that have postId equal to 2
		request.queryParam("postId", "2");

		// Make a request to the server by Specifying the Method utilized is a GET
		response = request.get("/comments");

		// Printing the JSON data from the server using the Body to the console
		System.out.println("METHOD GET "+response.body().asString());

		// Verify if the Status code is valid
		Assert.assertEquals(200, response.getStatusCode());

		// Verify the email for the third object is equal to Presley.Mueller@myrl.com
		Assert.assertEquals("Presley.Mueller@myrl.com", response.jsonPath().getString("email[0]"));
	}

	/** POST METHOD **/
	@Test(priority = 2)
	public void postDataApi() {
		// passing the JSON Data to the request
		request.body(inputBody);

		// Make a request to the server by Specifying the Method utilized is a POST
		response = request.post("/posts");

		// Printing the JSON data from the server using the Body to the console
		System.out.println("METHOD POST :" + response.body().asString());

		// Verify if the Status code is valid
		Assert.assertEquals(201, response.getStatusCode());
		// Verify the title is present
		Assert.assertTrue(response.jsonPath().getString("title").equalsIgnoreCase("Developper"));
		// Verify the body is present
		Assert.assertEquals("Java", response.jsonPath().getString("body"));
		// Verify the userId is present
		Assert.assertEquals("1", response.jsonPath().getString("userId"));
		// the id will be auto generated and it might be difficult to verify if you do
		// not get it first
		// Assert.assertEquals("101",response.jsonPath().getString("id"));
	}

	/** PUT METHOD **/
	@Test(priority = 3)
	public void putDataApi() {
		// change the value of the title using the replace method from String class
		inputBody = inputBody.replace("Developper", "Programmer");

		// passing the JSON Data to the request
		request.body(inputBody);

		// Make a request to the server by Specifying the Method utilized is a PUT
		response = request.put("/posts/1");

		// Printing the JSON data from the server using the Body to the console
		System.out.println("METHOD PUT :" + response.body().asString());

		// Verify the Title has changed
		Assert.assertEquals("Programmer", response.jsonPath().getString("title"));
	}

	@Test(priority = 4)
	public void patchDataApi() {
		/**
		 * this is a sample to show you that you could use String instead of JSONObject
		 * inputBody ="{\n" + " \"title\": \"Software Engineer\" \n}";
		 * request.body(inputBody);
		 **/

		// pass the data using the JSONObject see Global Variable on the Top as
		// reference
		requestParams.put("title","Software Engineer");

		// Passing the JSONObject to the Body using the toJSONString method to generate
		// a string
		request.body(requestParams.toJSONString());

		// Make a request to the server by Specifying the Method utilized is a PATCH
		response = request.patch("/posts/1");

		// Printing the JSON data from the server using the Body to the console
		System.out.println("METHOD PATCH "+response.body().asString());

		// Verify the Title has changed
		Assert.assertEquals("Software Engineer", response.jsonPath().getString("title"));
	}


	@Test(priority = 5)
	public void deleteDataApi() {
		// Make a request to the server by Specifying the Method utilized is a DELETE
		response = request.delete("/posts/1");

		// Printing the JSON data from the server using the Body to the console
		System.out.println("METHOD DELETE " +response.body().asString());

		// Verify the Status Code
		Assert.assertEquals(200, response.getStatusCode());
	}
	@Test(priority =6)
	public void deserializationTest() {
		// Make a request to the server by Specifying the Method utilized is a Get
		response = request.get("/comments");
		
		
		System.out.println(" DESERIALISATION BODY: "+response.body().asString());

		//Deserialize  the body to pass it to the Object Array 
		ResponseBodyDeserialization[] res= response.body().as(ResponseBodyDeserialization[].class);
		
		//passing the parameter to target only the json object that is equal to the first object
		request.queryParam("email",res[0].getEmail());
	    response  = request.get("/comments");
	    
	    System.out.println("THE RESPONSE FOR THE FIRST OBJECT " +response.asString());
		//if you want to print the contain of the Object remove comments of the code bellow
		
		/*
		 * for(ResponseBodyDeserialization r1 :res ) {
		 * System.out.println("THE DESERIALIZATION TEST "+r1.toString()); }
		 */
		
	    //verify the object property email is the same as the json email object
		Assert.assertTrue((res[0].getEmail().replace("[", "").replace("]", "")).equalsIgnoreCase(response.jsonPath().getString("email").replace("[", "").replace("]", "")));
		 //verify the object property name is the same as the json name object
		Assert.assertTrue((res[0].getName().replace("[", "").replace("]", "")).equalsIgnoreCase(response.jsonPath().getString("name").replace("[", "").replace("]", "")));
		//verify the object property body is the same as the json body object
		Assert.assertTrue((res[0].getBody().replace("[", "").replace("]", "")).equalsIgnoreCase(response.jsonPath().getString("body").replace("[", "").replace("]", "")));
		//verify the object property id is equal as the json id object
		Assert.assertEquals(res[0].getId(), Integer.parseInt(response.jsonPath().getString("id").replace("[", "").replace("]", "")));
		//verify the object property postId is equal as the json postId object
		Assert.assertEquals(res[0].getPostId(), Integer.parseInt(response.jsonPath().getString("postId").replace("[", "").replace("]", "")));
	}

	@Test(priority =7)
	public void deserializedObjectTest() {
		//passing the String to the Body
		request.body(inputBody);
		// Make a request to the server by Specifying the Method utilized is a Post
		response = request.post("/posts");
		//Deserialize  the body to pass it to the Object
		DeserializedObject deserializeObject = response.body().as(DeserializedObject.class);
		
		System.out.println("METHOD DESERIALIZATION "+ deserializeObject);

		//Verify StatusCode
		Assert.assertEquals(201, response.getStatusCode());
		//verify the object property title is equal as the json title object
		Assert.assertEquals(deserializeObject.getTitle(), response.jsonPath().getString("title"));
		//verify the object property id is equal as the json id object
		Assert.assertEquals(deserializeObject.getId(), response.jsonPath().getInt("id"));
		//verify the object property userId is equal as the json userId object
		Assert.assertEquals(deserializeObject.getUserId(), response.jsonPath().getInt("userId"));
		//verify the object property body is equal as the json body object
		Assert.assertEquals(deserializeObject.getBody(), response.jsonPath().getString("body"));
	}


}
