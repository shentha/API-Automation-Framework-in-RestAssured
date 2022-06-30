package jobsapiautomation;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.junit.Assert.assertThat;

import org.testng.annotations.Test;

import io.restassured.response.Response;

public class JobsSchemaValidation {
	
	@Test (priority = 9)
	 void validateSchema( ) {
			try {
				Response response = given().when().get();
        		// Schema Validation 
				String responseJsonStr = response.getBody().asString().replaceAll("NaN", "null");
				assertThat ( responseJsonStr, matchesJsonSchemaInClasspath("schemas\\JobsSchema.json"));
			}
			catch(Exception e ) {
				System.out.println( "Exception " + e.getMessage()); 
			}
		}  
}
