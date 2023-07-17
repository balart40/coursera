package tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class BestBuyAPITesting {
	
	@BeforeClass
	public void setup() {
		RestAssured.baseURI =  "http://localhost/";
		RestAssured.port = 3030;
	}

	@Test
	public void verifyGetProduct() {
		
		RestAssured.given().when().get("/products").then().statusCode(200);
	}
	
	@Test
	public void verifyGetProductWithSpecificId() {
		
		RestAssured.given()
			.param("id", 43900)
			.when().get("/products")
			.then().log().all().statusCode(200);
	}
	
	@Test
	public void verifyGetProductWithLimit() {
		
		RestAssured.given()
			.queryParam("$limit", 5)
			.when().get("/products")
			.then().log().all().statusCode(200);
	}
	
	@Test
	public void verifyPostRequest() {
		String requestPayload = "{\n"
				+ "  \"name\": \"Samsung Mobile\",\n"
				+ "  \"type\": \"Mobile\",\n"
				+ "  \"price\": 200,\n"
				+ "  \"shipping\": 50,\n"
				+ "  \"upc\": \"string\",\n"
				+ "  \"description\": \"Best Samsumg Mobile\",\n"
				+ "  \"manufacturer\": \"Samsung\",\n"
				+ "  \"model\": \"M51\",\n"
				+ "  \"url\": \"string\",\n"
				+ "  \"image\": \"string\"\n"
				+ "}";
		
		RestAssured.given().contentType(ContentType.JSON)
			.body(requestPayload)
			.when().post("/products")
			.then().statusCode(201).log().all();
	}
	
	@Test
	public void verifyPostProductWithPayloadAsObject() {
		
		Map<String, Object> requestPayload = new HashMap<>();
		
		requestPayload.put("name", "iPhone12");
		requestPayload.put("type", "iPhone Mobile");
		requestPayload.put("upc", "iPhone 12");
		requestPayload.put("price", 500);
		requestPayload.put("shipping", 10);
		requestPayload.put("description", "iPhone 12");
		requestPayload.put("model", "iPhone 12");
		requestPayload.put("manufacturer", "Apple");
		
		RestAssured.given().contentType(ContentType.JSON)
			.body(requestPayload)
			.when().post("/products")
			.then().statusCode(201).log().all();
		
		
	}
	
	@Test
	public void verifyUpdateProductWithPayloadAsObject() {
		
		Map<String, Object> requestPayload = new HashMap<>();
		
		requestPayload.put("name", "iPhone12");
		requestPayload.put("type", "iPhone Mobile");
		requestPayload.put("upc", "iPhone 12");
		requestPayload.put("price", 500);
		requestPayload.put("shipping", 10);
		requestPayload.put("description", "iPhone 12");
		requestPayload.put("model", "iPhone 12");
		requestPayload.put("manufacturer", "Apple");
		
		int productId = RestAssured.given().contentType(ContentType.JSON)
			.body(requestPayload)
			.when().post("/products")
			.then().extract().path("id");
		
		requestPayload.put("name", "iPhone13");
		requestPayload.put("type", "iPhone Mobile");
		requestPayload.put("upc", "iPhone 12");
		requestPayload.put("price", 500);
		requestPayload.put("shipping", 10);
		requestPayload.put("description", "iPhone 12");
		requestPayload.put("model", "iPhone 12");
		requestPayload.put("manufacturer", "Apple");
		
		RestAssured.given().contentType(ContentType.JSON)
		.body(requestPayload)
		.when().put("/products/" + productId)
		.then().statusCode(200).log().all();
		
	}
	
	@Test
	public void verifyDeleteProductWithPayloadAsObject() {
		
		Map<String, Object> requestPayload = new HashMap<>();
		
		requestPayload.put("name", "iPhone12");
		requestPayload.put("type", "iPhone Mobile");
		requestPayload.put("upc", "iPhone 12");
		requestPayload.put("price", 500);
		requestPayload.put("shipping", 10);
		requestPayload.put("description", "iPhone 12");
		requestPayload.put("model", "iPhone 12");
		requestPayload.put("manufacturer", "Apple");
		
		int productId = RestAssured.given().contentType(ContentType.JSON)
			.body(requestPayload)
			.when().post("/products")
			.then().extract().path("id");
		
		RestAssured.given().contentType(ContentType.JSON)
		.when().delete("/products/" + productId)
		.then().statusCode(200).log().all();
		
		RestAssured.given().contentType(ContentType.JSON)
		.when().get("/products/" + productId)
		.then().statusCode(404).log().all();
		
	}
	
	
}
