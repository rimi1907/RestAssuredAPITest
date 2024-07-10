package testCases;
import static  io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.response.Response;

public class TC_VideoGameAPI {

	@Test(priority=1)
	public void  test_getAllVideoGames()
	{
		given()
		.when().get("http://localhost:8080/app/videogames")
		.then().statusCode(200);
	}
	@Test(priority=2)
	public void test_addNewVideoGames()
	{	HashMap data = new HashMap();
	
		data.put("id", "17");
		data.put("name", "Spider-Man");
		data.put("releaseDate", "2024-07-10T13:07:52.806Z");
		data.put("reviewScore", "5");
		data.put("category", "Adventure");
		data.put("rating", "Universal");
		
		
		Response res= 
		given().contentType("application/json")
		.body(data)
		.when().post("http://localhost:8080/app/videogames")
		.then()
		.statusCode(200).log().body().extract().response();
		
		String jsonString =res.asString();
		
		Assert.assertEquals(jsonString.contains("Record Added Successfully"), true );
	}
	
	@Test(priority=3)
	 public void test_getVideoGame()
	 {
		given().
		when().get("http://localhost:8080/app/videogames/17")
		.then()
		.statusCode(200)
		.log().body().
		body("videoGame .id", equalTo("17"))
		.body("videoGame .name", equalTo("Spider-Man"));
	 }
	
	
	@Test(priority=4)
	public void test_updateVideoGame()
	{
		
		HashMap data = new HashMap();
		
		data.put("id", "17");
		data.put("name", "Bat-Man");
		data.put("releaseDate", "2024-07-10T13:17:52.806Z");
		data.put("reviewScore", "4");
		data.put("category", "Adventure");
		data.put("rating", "Universal");
		
		given()
		.contentType("Application/json")
		.body(data)
		.when()
		.put("http://localhost:8080/app/videogames/17")
		.then().statusCode(200)
		.log().body()
		.body("videoGame .id", equalTo("17"))
		.body("videoGame .name", equalTo("Bat-Man"))
		.body("videoGame .reviewScore", equalTo("4"));
		

		}
	
	
	@Test(priority=5)
	
	public void test_deleteVideoGame()
	{
	Response res=	given()
		.when()
		.delete("http://localhost:8080/app/videogames/17")
		.then()
		.statusCode(200)
		.log().body()
		.extract().response();
	
	String jsonString =res.asString();
	
	Assert.assertEquals(jsonString.contains("Record Deleted Successfully"), true);
	}
}
